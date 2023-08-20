package team.youngcha.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.enums.Gender;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.estimate.repository.EstimateRepository;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.keyword.entity.Keyword;
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

    public List<FindSelfOptionResponse> findSelfRequiredOptions(Long trimId, RequiredCategory category) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> options = optionRepository.findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, category);

        return buildFindSelfRequiredOptionResponses(trimId, options, category);
    }

    public List<FindSelfOptionResponse> findSelfInteriorColors(Long trimId, Long exteriorColorId) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> options = optionRepository
                .findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId);

        return buildFindSelfRequiredOptionResponses(trimId, options, RequiredCategory.INTERIOR_COLOR);
    }

    public List<FindSelfOptionResponse> findSelfSelectiveOptions(Long trimId) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));

        List<Option> options = optionRepository.findOptionsByTrimIdAndOptionType(trimId, OptionType.SELECTIVE);

        return buildFindSelfSelectiveOptionResponses(trimId, options);
    }

    public List<FindGuideOptionResponse> findGuideOptions(Long trimId, GuideInfo guideInfo, RequiredCategory category) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
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
        RequiredCategory category = RequiredCategory.EXTERIOR_COLOR;

        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));

        List<Option> exteriorColors
                = optionRepository.findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, RequiredCategory.EXTERIOR_COLOR);

        return getFindGuideColorOptionResponse(trimId, guideInfo, category, exteriorColors);
    }

    public List<FindGuideOptionResponse> findGuideModeInteriorColors(Long trimId, GuideInfo guideInfo, Long exteriorColorId) {
        RequiredCategory category = RequiredCategory.INTERIOR_COLOR;

        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));

        List<Option> interiorColors
                = optionRepository.findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId);

        return getFindGuideColorOptionResponse(trimId, guideInfo, category, interiorColors);
    }

    private List<FindGuideOptionResponse> getFindGuideColorOptionResponse(Long trimId, GuideInfo guideInfo, RequiredCategory category, List<Option> options) {
        List<Long> optionIds = options.stream().map(Option::getId).collect(Collectors.toList());

        // 옵션 이미지
        Map<Long, List<OptionImage>> exteriorColorImagesGroup = getOptionImagesGroup(optionIds);

        // 옵션 상세설명
        Map<Long, List<OptionDetail>> exteriorColorDetailsGroup = getOptionDetailGroup(optionIds);

        // 유사 사용자 비율
        Map<Long, Integer> similarityUsersRatio = getSimilarityUsersRatio(trimId, optionIds, guideInfo, category);

        // 연령대별 판매량
        Map<Long, Long> sellCountByAgeRange
                = sellRepository.countOptionsByTrimIdAndAgeRange(trimId, category, guideInfo.getAgeRange());

        // 연령대별 판매율
        Map<Long, Integer> sellRatioByAgeRange = calculateOptionRatios(sellCountByAgeRange);

        // 성별을 선택하지 않은 경우, 동일 연령대를 기준으로 가장 많이 판매된 옵션을 추천
        if (guideInfo.getGender() == Gender.NONE) {
            Map<Long, List<KeywordRate>> keywordRateGroup = getKeywordGroupOfAgeRange(guideInfo, optionIds, sellRatioByAgeRange);

            return buildFindGuideOptionResponseSortedBySellRatio(options, sellRatioByAgeRange, similarityUsersRatio,
                    keywordRateGroup, exteriorColorImagesGroup, exteriorColorDetailsGroup);
        }

        // 성별별 판매량
        Map<Long, Long> sellCountByGender
                = sellRepository.countOptionsByTrimIdAndGender(trimId, category, guideInfo.getGender());

        // 성별별 판매율
        Map<Long, Integer> sellRatioByGender = calculateOptionRatios(sellCountByGender);

        // 키워드 그룹
        Map<Long, List<KeywordRate>> keywordRateGroup = getKeywordGroupOfAgeRangeAndGender(guideInfo, optionIds, sellRatioByAgeRange, sellRatioByGender);

        // 성별 및 연령대별 옵션 판매량
        Map<Long, Long> sellCountByAgeRangeAndGender
                = sellRepository.countOptionsByTrimIdAndAgeRangeAndGender(trimId, category, guideInfo.getAgeRange(), guideInfo.getGender());
        Map<Long, Integer> sellRatioByAgeRangeAndGender = calculateOptionRatios(sellCountByAgeRangeAndGender);

        // 동일 성별 및 연령대를 기준으로 가장 많이 판매된 옵션을 추천
        return buildFindGuideOptionResponseSortedBySellRatio(options, sellRatioByAgeRangeAndGender, similarityUsersRatio,
                keywordRateGroup, exteriorColorImagesGroup, exteriorColorDetailsGroup);
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
            double ratio = total > 0? ((double) count * 100 / total) : 0L;
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
}
