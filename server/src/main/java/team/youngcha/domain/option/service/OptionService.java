package team.youngcha.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.estimate.repository.EstimateRepository;
import team.youngcha.domain.estimate.repository.EstimateSelectiveOptionRepository;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.keyword.entity.Keyword;
import team.youngcha.domain.keyword.enums.KeywordName;
import team.youngcha.domain.keyword.repository.KeywordRepository;
import team.youngcha.domain.option.dto.FindGuideOptionResponse;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
import team.youngcha.domain.option.dto.GuideInfo;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionDetail;
import team.youngcha.domain.option.entity.OptionImage;
import team.youngcha.domain.option.enums.OptionType;
import team.youngcha.domain.option.repository.OptionDetailRepository;
import team.youngcha.domain.option.repository.OptionImageRepository;
import team.youngcha.domain.option.repository.OptionRepository;
import team.youngcha.domain.sell.repository.SellRepository;
import team.youngcha.domain.sell.repository.SellSelectiveOptionRepository;
import team.youngcha.domain.trim.repository.TrimRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final TrimRepository trimRepository;
    private final SellRepository sellRepository;
    private final OptionRepository optionRepository;
    private final KeywordRepository keywordRepository;
    private final EstimateRepository estimateRepository;
    private final OptionImageRepository optionImageRepository;
    private final OptionDetailRepository optionDetailRepository;
    private final SellSelectiveOptionRepository sellSelectiveOptionRepository;
    private final EstimateSelectiveOptionRepository estimateSelectiveOptionRepository;

    public List<FindSelfOptionResponse> findSelfRequiredOptions(Long trimId, RequiredCategory category) {
        verifyTrimId(trimId);

        List<Option> options = optionRepository.findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, category);

        return buildFindSelfRequiredOptionResponses(trimId, options, category);
    }

    public List<FindSelfOptionResponse> findSelfInteriorColors(Long trimId, Long exteriorColorId) {
        verifyTrimId(trimId);

        List<Option> options = optionRepository
                .findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId);

        return buildFindSelfRequiredOptionResponses(trimId, options, RequiredCategory.INTERIOR_COLOR);
    }

    public List<FindSelfOptionResponse> findSelfSelectiveOptions(Long trimId) {
        verifyTrimId(trimId);

        List<Option> options = optionRepository.findByTrimIdAndOptionType(trimId, OptionType.SELECTIVE);

        return buildFindSelfSelectiveOptionResponses(trimId, options);
    }

    public List<FindGuideOptionResponse> findGuideRequiredOptions(Long trimId, GuideInfo guideInfo, RequiredCategory category) {
        verifyTrimId(trimId);

        List<Option> options = optionRepository
                .findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, category);

        List<Long> optionsId = options.stream().map(Option::getId).collect(Collectors.toList());

        Map<Long, List<OptionImage>> optionImagesGroup = getOptionImagesGroup(optionsId);
        Map<Long, List<OptionDetail>> optionDetailsGroup = getOptionDetailGroup(optionsId);

        // 트림에 해당하는 파워 트레인 별 유사 사용자 수 비율
        Map<Long, Integer> similarityUsersRatio = getSimilarityUsersRatio(trimId, optionsId,
                guideInfo, category);

        // 옵션 별 키워드 조회
        Map<Long, List<Keyword>> optionIdKeywordGroup = keywordRepository
                .findByContainOptionIdsAndGroupKeywords(optionsId);

        Map<Long, List<KeywordRate>> keywordRateGroup = findKeywordRateGroup(guideInfo, optionIdKeywordGroup,
                trimId, category);

        return getSortedGuideOptionResponses(options, similarityUsersRatio, keywordRateGroup,
                optionImagesGroup, optionDetailsGroup);
    }

    public List<FindGuideOptionResponse> findGuideModeExteriorColors(Long trimId, GuideInfo guideInfo) {
        verifyTrimId(trimId);

        RequiredCategory category = RequiredCategory.EXTERIOR_COLOR;

        List<Option> exteriorColors
                = optionRepository.findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, RequiredCategory.EXTERIOR_COLOR);

        return getFindGuideColorOptionResponse(trimId, guideInfo, category, exteriorColors);
    }

    public List<FindGuideOptionResponse> findGuideModeInteriorColors(Long trimId, GuideInfo guideInfo, Long exteriorColorId) {
        RequiredCategory category = RequiredCategory.INTERIOR_COLOR;

        verifyTrimId(trimId);

        List<Option> interiorColors
                = optionRepository.findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId);

        return getFindGuideColorOptionResponse(trimId, guideInfo, category, interiorColors);
    }

    public List<FindGuideOptionResponse> findGuideModeWheel(Long trimId, GuideInfo guideInfo, Long exteriorColorId) {
        RequiredCategory wheelCategory = RequiredCategory.WHEEL;

        // 트림 아이디 검증
        verifyTrimId(trimId);

        // 사용자 선택 키워드
        List<Long> userKeywordIds = guideInfo.getKeywordIds();

        // 트림의 모든 휠 옵션
        Map<Long, Option> trimWheels = optionRepository.findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, wheelCategory)
                .stream()
                .collect(Collectors.toMap(Option::getId, option -> option));

        // 기본 휠 옵션
        Option defaultWheel = getDefaultWheel();

        // 트림별 키워드 그룹
        Map<Long, List<Keyword>> keywordGroups = keywordRepository
                .findByContainOptionIdsAndGroupKeywords(new ArrayList<>(trimWheels.keySet()));

        // 응답에 포함될 휠 옵션의 아이디 목록
        // 사용자가 선택한 키워드에 해당하는 모든 옵션을 응답에 포함
        List<Long> responseWheelIds = keywordGroups.entrySet()
                .stream()
                .filter(entry -> entry.getValue().stream().anyMatch(keyword -> userKeywordIds.contains(keyword.getId())))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 기본 휠 옵션을 응답에 포함
        if (!responseWheelIds.contains(defaultWheel.getId())) {
            responseWheelIds.add(defaultWheel.getId());
        }

        // 유사 사용자의 휠 옵션 선택 비율
        Map<Long, Integer> similarityUsersRatio = getSimilarityUsersRatio(trimId, responseWheelIds, guideInfo, wheelCategory);

        // 휠 옵션 상세정보
        Map<Long, List<OptionDetail>> optionDetailGroup = getOptionDetailGroup(responseWheelIds);

        // 휠 옵션 이미지
        Map<Long, List<OptionImage>> optionImagesGroup = getOptionImagesGroup(responseWheelIds);

        // 사용자 선택 키워드를 기반으로 추천할 휠의 이름을 조회
        String recommendedWheelName = getRecommendedWheelName(trimId, exteriorColorId, userKeywordIds);

        // 옵션별 응답 DTO를 생성하여 추천하는 휠을 첫번째로, 그 외에는 유사 사용자의 옵션 선택 비율을 기준으로 내림차순으로 정렬하여 응답
        return responseWheelIds.stream()
                .map(id -> {
                    Option option = trimWheels.get(id);
                    boolean checked = option.getName().equals(recommendedWheelName);
                    return new FindGuideOptionResponse(
                            option,
                            checked,
                            similarityUsersRatio.get(id),
                            null,
                            optionImagesGroup.get(id),
                            optionDetailGroup.get(id));
                })
                .sorted(Comparator.comparingDouble((FindGuideOptionResponse response) -> response.isChecked() ? 0 : 1)
                        .thenComparing((FindGuideOptionResponse response) -> -response.getRate()))
                .collect(Collectors.toList());
    }

    public List<FindGuideOptionResponse> findGuideSelectiveOptions(Long trimId, GuideInfo guideInfo) {
        // 사용자 선택 키워드 목록
        List<Long> userKeywordIds = guideInfo.getKeywordIds();

        // 키워드 이름 정보
        Map<Long, String> keywordNames = keywordRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Keyword::getId,
                        Keyword::getName
                ));

        // 트림의 선택 옵션 목록
        List<Option> selectiveOptions
                = optionRepository.findByTrimIdAndOptionType(trimId, OptionType.SELECTIVE);

        // 선택 옵션의 키워드 목록
        Map<Long, List<Keyword>> optionKeywords = keywordRepository.findByContainOptionIdsAndGroupKeywords(
                selectiveOptions.stream().map(Option::getId).collect(Collectors.toList()));

        // 키워드별 해당 견적 아이디 목록 (estimateIds.get(0) : 1순위 키워드가 포함되는 견적 아이디)
        Map<Long, List<Long>> estimateIds = userKeywordIds.stream()
                .collect(Collectors.toMap(
                        keywordId -> keywordId,
                        keywordId -> estimateRepository.findIdsByTrimIdAndKeywordId(trimId, keywordId)));

        // 유사 사용자의 견적 목록
        List<Long> estimateIdsOfSimilarUsers =
                estimateRepository.findEstimateIdsOfSimilarUsers(trimId, guideInfo);

        // 응답에 포함될 선택 옵션 목록
        List<Option> responseOptions = new ArrayList<>();

        // 옵션의 키워드별 선택률
        Map<Long, List<KeywordRate>> keywordRateGroup = new HashMap<>();

        // 옵션의 유사 사용자 선택률
        Map<Long, Integer> similarUserRatios = new HashMap<>();

        // 트림의 모든 선택 옵션에 대해 우선순위가 높은 키워드부터 해당 옵션에 포함되는지 확인
        // 옵션에 키워드가 포함된다면 '해당 키워드의 옵션 선택률'과 '유사 사용자의 옵션 선택률'을 계산하여 응답에 포함
        //   '키워드 옵션 선택률' : (특정 키워드와 옵션이 포함된 견적의 수) / (해당 키워드가 포함된 견적의 수)
        //   '유사 사용자의 옵션 선택률' : (특정 옵션이 포함된 유사 유저의 견적 수) / (유사 유저의 전체 견적 수)
        for (Option option : selectiveOptions) {
            Long optionId = option.getId();
            boolean isKeywordMatched = false;

            // 옵션의 키워드 목록
            List<Long> optionKeywordIds = optionKeywords.get(optionId)
                    .stream()
                    .map(Keyword::getId)
                    .collect(Collectors.toList());

            // 우선 순위가 높은 키워드부터 해당 옵션에 포함되는지 확인
            for (Long userKeywordId : userKeywordIds) {
                if (optionKeywordIds.contains(userKeywordId)) {
                    isKeywordMatched = true;

                    // 키워드의 옵션 선택률
                    Long sellCount = estimateSelectiveOptionRepository
                            .countIfEstimateIncludeOption(estimateIds.get(userKeywordId), option);
                    int keywordRate = Math.toIntExact(100 * sellCount / estimateIds.get(userKeywordId).size());

                    // 유사 사용자의 옵션 선택률
                    Long sellCountOfSimilarUser = estimateSelectiveOptionRepository.countIfEstimateIncludeOption(estimateIdsOfSimilarUsers, option);
                    int similarRate = Math.toIntExact(100 * sellCountOfSimilarUser / estimateIdsOfSimilarUsers.size());

                    // 응답에 옵션 정보와 키워드 및 유사 사용자 기준 선택률을 포함
                    responseOptions.add(option);
                    similarUserRatios.put(optionId, similarRate);

                    List<KeywordRate> optionKeywordRates = keywordRateGroup.computeIfAbsent(optionId, o -> new ArrayList<>());
                    optionKeywordRates.add(new KeywordRate(keywordRate, keywordNames.get(userKeywordId)));
                }

                // 우선 순위가 높은 키워드가 옵션에 포함되어 이미 처리되었다면, 나머지 키워드는 생략
                if (isKeywordMatched) {
                    break;
                }
            }
        }

        // 응답에 포함될 옵션의 목록
        List<Long> responseOptionIds = responseOptions.stream().map(Option::getId).collect(Collectors.toList());

        // 옵션 이미지
        Map<Long, List<OptionImage>> optionImagesGroup = getOptionImagesGroup(responseOptionIds);

        // 옵션 상세정보
        Map<Long, List<OptionDetail>> optionDetailsGroup = getOptionDetailGroup(responseOptionIds);

        return getSortedGuideOptionResponses(responseOptions, similarUserRatios, keywordRateGroup, optionImagesGroup, optionDetailsGroup);
    }

    private Option getDefaultWheel() {
        return findOptionByName("20인치 알로이 휠");
    }

    private String getRecommendedWheelName(Long trimId, Long exteriorColorId, List<Long> userSelectedKeywordIds) {
        Map<String, Long> keywords = keywordRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Keyword::getName, Keyword::getId));

        if (userSelectedKeywordIds.contains(keywords.get(KeywordName.DESIGN.getName()))) {
            if (userSelectedKeywordIds.contains(keywords.get(KeywordName.DRIVING_PERFORMANCE.getName())) ||
                    userSelectedKeywordIds.contains(keywords.get(KeywordName.SAFETY.getName()))) {
                return "알콘(alcon) 단조 브레이크 & 20인치 휠 패키지";
            }

            return getRecommendedWheelNameBySellCount(trimId, exteriorColorId);
        }

        return "20인치 알로이 휠";
    }

    private String getRecommendedWheelNameBySellCount(Long trimId, Long exteriorColorId) {
        Option darkSputteringWheel = findOptionByName("20인치 다크 스퍼터링 휠");
        Option blacktoneWheel = findOptionByName("20인치 블랙톤 전면 가공 휠");

        Map<Long, Long> sellCounts = sellRepository.countByTrimIdAndExteriorColorForWheels(
                trimId,
                exteriorColorId,
                List.of(darkSputteringWheel.getId(), blacktoneWheel.getId())
        );

        if (sellCounts.get(darkSputteringWheel.getId()) > sellCounts.get(blacktoneWheel.getId())) {
            return darkSputteringWheel.getName();
        }
        return blacktoneWheel.getName();
    }

    private Option findOptionByName(String optionName) {
        return optionRepository.findByName(optionName)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "옵션 조회 실패: " + optionName));
    }

    private List<FindGuideOptionResponse> getFindGuideColorOptionResponse(Long trimId, GuideInfo guideInfo, RequiredCategory category, List<Option> options) {
        List<Long> optionIds = options.stream().map(Option::getId).collect(Collectors.toList());

        // 옵션 이미지
        Map<Long, List<OptionImage>> exteriorColorImagesGroup = getOptionImagesGroup(optionIds);

        // 옵션 상세설명
        Map<Long, List<OptionDetail>> exteriorColorDetailsGroup = getOptionDetailGroup(optionIds);

        // 유사 사용자 비율
        Map<Long, Integer> similarityUsersRatio = getSimilarityUsersRatio(trimId, optionIds, guideInfo, category);

        // 동일 연령대의 옵션별 구매율
        Map<Long, Integer> sellRatioByAgeRange = getOptionSellRatioByTrimIdAndCategoryAndAgeRange(trimId, category, guideInfo.getAgeRange());

        // 성별을 선택하지 않은 경우, 동일 연령대를 기준으로 가장 많이 판매된 옵션을 추천
        if (guideInfo.getGender() == Gender.NONE) {
            Map<Long, List<KeywordRate>> keywordRateGroup = getKeywordGroupOfAgeRange(guideInfo, optionIds, sellRatioByAgeRange);

            return buildFindGuideOptionResponseSortedBySellRatio(options, sellRatioByAgeRange, similarityUsersRatio,
                    keywordRateGroup, exteriorColorImagesGroup, exteriorColorDetailsGroup);
        }

        // 동일 성별의 옵션별 구매율
        Map<Long, Integer> sellRatioByGender = getOptionSellRatioByTrimIdAndCategoryAndGender(trimId, category, guideInfo.getGender());

        // 키워드 그룹
        Map<Long, List<KeywordRate>> keywordRateGroup = getKeywordGroupOfAgeRangeAndGender(guideInfo, optionIds, sellRatioByAgeRange, sellRatioByGender);

        // 동일 성별 및 연령대의 옵션별 구매율
        Map<Long, Integer> sellRatioByAgeRangeAndGender =
                getOptionSellRatioByTrimIdAndCategoryAndAgeRangeAndGender(trimId, category, guideInfo.getAgeRange(), guideInfo.getGender());

        // 동일 성별 및 연령대를 기준으로 가장 많이 판매된 옵션을 추천
        return buildFindGuideOptionResponseSortedBySellRatio(options, sellRatioByAgeRangeAndGender, similarityUsersRatio,
                keywordRateGroup, exteriorColorImagesGroup, exteriorColorDetailsGroup);
    }

    private Map<Long, Integer> getOptionSellRatioByTrimIdAndCategoryAndAgeRange(Long trimId, RequiredCategory category, AgeRange ageRange) {
        // 동일 연령대의 옵션별 구매량
        Map<Long, Long> sellCountByAgeRange
                = sellRepository.countOptionsByTrimIdAndAgeRange(trimId, category, ageRange);

        // 동일 연령대의 옵션별 구매율
        return calculateOptionRatios(sellCountByAgeRange);
    }

    private Map<Long, Integer> getOptionSellRatioByTrimIdAndCategoryAndGender(Long trimId, RequiredCategory category, Gender gender) {
        // 동일 성별의 옵션별 구매량
        Map<Long, Long> sellCountByGender
                = sellRepository.countOptionsByTrimIdAndGender(trimId, category, gender);

        // 동일 성별의 옵션별 구매율
        return calculateOptionRatios(sellCountByGender);
    }

    private Map<Long, Integer> getOptionSellRatioByTrimIdAndCategoryAndAgeRangeAndGender(Long trimId,
                                                                                         RequiredCategory category,
                                                                                         AgeRange ageRange,
                                                                                         Gender gender) {
        // 동일 성별 및 연령대의 옵션별 구매량
        Map<Long, Long> sellCountByAgeRangeAndGender
                = sellRepository.countOptionsByTrimIdAndAgeRangeAndGender(trimId, category, ageRange, gender);

        // 동일 성별 및 연령대의 옵션별 구매율
        return calculateOptionRatios(sellCountByAgeRangeAndGender);
    }

    private List<FindSelfOptionResponse> buildFindSelfRequiredOptionResponses(Long trimId, List<Option> options,
                                                                              RequiredCategory category) {
        List<Long> optionsIds = options.stream().map(Option::getId).collect(Collectors.toList());
        Map<Long, Integer> sellRatio = getRequiredOptionSellRatio(trimId, optionsIds, category);
        Map<Long, List<OptionImage>> optionImagesGroup = getOptionImagesGroup(optionsIds);
        Map<Long, List<OptionDetail>> optionDetailsGroup = getOptionDetailGroup(optionsIds);

        return getSortedSelfOptionResponses(options, sellRatio, optionImagesGroup, optionDetailsGroup);
    }

    private List<FindSelfOptionResponse> buildFindSelfSelectiveOptionResponses(Long trimId, List<Option> options) {
        List<Long> optionsIds = options.stream().map(Option::getId).collect(Collectors.toList());
        Map<Long, Integer> sellRatio = getSelectiveOptionSellRatio(trimId, optionsIds);
        Map<Long, List<OptionImage>> optionImagesGroup = getOptionImagesGroup(optionsIds);
        Map<Long, List<OptionDetail>> optionDetailsGroup = getOptionDetailGroup(optionsIds);

        return getSortedSelfOptionResponses(options, sellRatio, optionImagesGroup, optionDetailsGroup);
    }

    private Map<Long, Integer> getRequiredOptionSellRatio(Long trimId, List<Long> optionsIds, RequiredCategory category) {
        Map<Long, Long> optionCounts = sellRepository
                .countOptionsByTrimIdAndContainOptionsIds(trimId, optionsIds, category);
        addMissingOptionIds(optionCounts, optionsIds);
        return calculateOptionRatios(optionCounts);
    }

    private Map<Long, Integer> getSelectiveOptionSellRatio(Long trimId, List<Long> optionsIds) {
        Map<Long, Long> optionCounts = sellSelectiveOptionRepository.countByOptionIdForTrim(trimId);
        addMissingOptionIds(optionCounts, optionsIds);
        return calculateOptionRatios(optionCounts);
    }

    private Map<Long, Integer> getSimilarityUsersRatio(Long trimId, List<Long> optionsIds,
                                                       GuideInfo guideInfo, RequiredCategory category) {
        Map<Long, Long> optionCounts = estimateRepository
                .countOptionsSimilarityUsers(trimId, optionsIds, guideInfo, category);
        addMissingOptionIds(optionCounts, optionsIds);
        return calculateOptionRatios(optionCounts);
    }

    private Map<Long, Integer> calculateOptionRatios(Map<Long, Long> optionCounts) {
        long total = optionCounts.values().stream().mapToLong(Long::longValue).sum();

        Map<Long, Integer> optionRatios = new HashMap<>();
        for (Map.Entry<Long, Long> entry : optionCounts.entrySet()) {
            Long optionId = entry.getKey();
            Long count = entry.getValue();
            double ratio = total > 0 ? ((double) count * 100 / total) : 0L;
            optionRatios.put(optionId, Math.round((float) ratio));
        }
        return optionRatios;
    }

    private Map<Long, List<OptionImage>> getOptionImagesGroup(List<Long> optionsIds) {
        List<OptionImage> optionsImages = optionImageRepository.findByContainOptionIds(optionsIds);
        return optionsImages.stream().collect(Collectors.groupingBy(OptionImage::getOptionId));
    }

    private Map<Long, List<OptionDetail>> getOptionDetailGroup(List<Long> optionsIds) {
        List<OptionDetail> optionsDetails = optionDetailRepository.findWithSpecsByContainOptionIds(optionsIds);
        return optionsDetails.stream().collect(Collectors.groupingBy(OptionDetail::getOptionId));
    }

    private void addMissingOptionIds(Map<Long, Long> counts, List<Long> optionIds) {
        for (Long optionId : optionIds) {
            counts.putIfAbsent(optionId, 0L);
        }
    }

    private List<FindSelfOptionResponse> getSortedSelfOptionResponses(List<Option> options, Map<Long, Integer> sellRatio,
                                                                      Map<Long, List<OptionImage>> optionImagesGroup,
                                                                      Map<Long, List<OptionDetail>> optionDetailsGroup) {
        return options.stream()
                .map(option -> new FindSelfOptionResponse(option,
                        sellRatio.get(option.getId()),
                        optionImagesGroup.getOrDefault(option.getId(), new ArrayList<>()),
                        optionDetailsGroup.getOrDefault(option.getId(), new ArrayList<>())))
                .sorted(Comparator.comparingDouble(response -> -response.getRate()))
                .collect(Collectors.toList());
    }

    private Map<Long, List<KeywordRate>> findKeywordRateGroup(GuideInfo guideInfo,
                                                              Map<Long, List<Keyword>> optionIdKeywordGroup,
                                                              Long trimId,
                                                              RequiredCategory category) {
        Map<Long, List<KeywordRate>> keywordRateGroup = new HashMap<>();

        for (Long keywordId : guideInfo.getKeywordIds()) {
            // 사용자 키워드 우선순위 순으로 옵션이 해당 키워드를 갖는지 확인
            List<Long> optionIdsWithKeyword = optionIdKeywordGroup.entrySet().stream()
                    .filter(entry -> entry.getValue().stream()
                            .anyMatch(keyword -> keyword.getId().equals(keywordId)))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // 겹치는 키워드가 옵션 하나에만 있으면 비율을 조회 후 반환
            if (optionIdsWithKeyword.size() == 1) {
                Long selectedOptionId = optionIdsWithKeyword.get(0);
                int rate = estimateRepository.calculateRate(trimId, selectedOptionId, keywordId, category);
                Keyword keyword = keywordRepository.findById(keywordId)
                        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "키워드를 찾을 수 없습니다."));
                KeywordRate keywordRate = new KeywordRate(rate, keyword.getName());
                keywordRateGroup.put(selectedOptionId, List.of(keywordRate));
                return keywordRateGroup;
            }
        }
        return keywordRateGroup;
    }

    private List<FindGuideOptionResponse> getSortedGuideOptionResponses(List<Option> options,
                                                                        Map<Long, Integer> similarityUsersRatio,
                                                                        Map<Long, List<KeywordRate>> keywordRateGroup,
                                                                        Map<Long, List<OptionImage>> optionImagesGroup,
                                                                        Map<Long, List<OptionDetail>> optionDetailsGroup) {
        return options.stream()
                .map(option -> {
                    Integer ratio = similarityUsersRatio.get(option.getId());
                    boolean isSelected = determineSelection(keywordRateGroup, similarityUsersRatio, option.getId(), ratio);
                    return new FindGuideOptionResponse(
                            option,
                            isSelected,
                            ratio,
                            keywordRateGroup.getOrDefault(option.getId(), new ArrayList<>()),
                            optionImagesGroup.getOrDefault(option.getId(), new ArrayList<>()),
                            optionDetailsGroup.getOrDefault(option.getId(), new ArrayList<>())
                    );
                })
                .sorted(Comparator.comparingDouble((FindGuideOptionResponse response) -> response.isChecked() ? 0 : 1) // 비율 내림차순
                        .thenComparing((FindGuideOptionResponse response) -> -response.getRate())) // true가 맨 처음
                .collect(Collectors.toList());
    }

    private boolean determineSelection(Map<Long, List<KeywordRate>> keywordRateGroup,
                                       Map<Long, Integer> similarityUsersRatio,
                                       Long optionId,
                                       Integer ratio) {
        if (!keywordRateGroup.isEmpty()) {
            return !keywordRateGroup.getOrDefault(optionId, Collections.emptyList()).isEmpty();
        }
        // 겹치는 키워드가 없으면 유사 사용자 비율이 높은 옵션을 선택
        boolean isMaxRate = ratio.equals(Collections.max(similarityUsersRatio.values()));
        if (isMaxRate) {
            similarityUsersRatio.put(optionId, Integer.MAX_VALUE);
        }
        return isMaxRate;
    }

    private Map<Long, List<KeywordRate>> getKeywordGroupOfAgeRange(GuideInfo guideInfo, List<Long> exteriorColorIds, Map<Long, Integer> sellRatioByAgeRange) {
        // 연령대 키워드 이름
        String keywordAgeRange = guideInfo.getAgeRange().toKeyword();

        Map<Long, KeywordRate> ageRangeKeywordRate = sellRatioByAgeRange.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new KeywordRate(entry.getValue(), keywordAgeRange)));

        return exteriorColorIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> List.of(ageRangeKeywordRate.get(id))
                ));
    }

    private Map<Long, List<KeywordRate>> getKeywordGroupOfAgeRangeAndGender(GuideInfo guideInfo, List<Long> exteriorColorIds, Map<Long, Integer> sellRatioByAgeRange, Map<Long, Integer> sellRatioByGender) {
        // 연령대 키워드 이름
        String keywordAgeRange = guideInfo.getAgeRange().toKeyword();

        // 성별 키워드 이름
        String keywordGender = guideInfo.getGender().toKeyword();

        Map<Long, KeywordRate> ageRangeKeywordRate = sellRatioByAgeRange.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new KeywordRate(entry.getValue(), keywordAgeRange)));

        Map<Long, KeywordRate> genderKeywordRate = sellRatioByGender.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new KeywordRate(entry.getValue(), keywordGender)));

        return exteriorColorIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> List.of(ageRangeKeywordRate.get(id),
                                genderKeywordRate.get(id))
                ));
    }

    private List<FindGuideOptionResponse> buildFindGuideOptionResponseSortedBySellRatio(List<Option> options,
                                                                                        Map<Long, Integer> sellRatio,
                                                                                        Map<Long, Integer> similarityUsersRatio,
                                                                                        Map<Long, List<KeywordRate>> keywordRateGroup,
                                                                                        Map<Long, List<OptionImage>> optionImagesGroup,
                                                                                        Map<Long, List<OptionDetail>> optionDetailsGroup) {
        List<FindGuideOptionResponse> responses = options.stream()
                .map(option -> {
                    Integer ratio = similarityUsersRatio.get(option.getId());
                    boolean isSelected = false;
                    return new FindGuideOptionResponse(
                            option,
                            isSelected,
                            ratio,
                            keywordRateGroup.getOrDefault(option.getId(), new ArrayList<>()),
                            optionImagesGroup.getOrDefault(option.getId(), new ArrayList<>()),
                            optionDetailsGroup.getOrDefault(option.getId(), new ArrayList<>())
                    );
                })
                .sorted(Comparator.comparingDouble((FindGuideOptionResponse response) -> -sellRatio.get(response.getId())))
                .collect(Collectors.toList());

        if (!responses.isEmpty()) {
            responses.get(0).setChecked(true);
        }

        return responses;
    }

    private void verifyTrimId(Long trimId) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
    }
}
