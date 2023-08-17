package team.youngcha.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.category.enums.SelectiveCategory;
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

    public List<FindSelfOptionResponse> findSelfOptions(Long trimId, SelectiveCategory category) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> options = optionRepository.findOptionsByTrimIdAndType(trimId, OptionType.OPTIONAL, category);

        List<Long> optionsIds = options.stream().map(Option::getId).collect(Collectors.toList());
        Map<Long, Integer> sellRatio = getSellRatio(trimId, optionsIds, category);
        Map<Long, List<OptionImage>> optionImagesGroup = getOptionImagesGroup(optionsIds);
        Map<Long, List<OptionDetail>> optionDetailsGroup = getOptionDetailGroup(optionsIds);

        return getSortedSelfOptionResponses(options, sellRatio, optionImagesGroup, optionDetailsGroup);
    }

    public List<FindGuideOptionResponse> findGuidePowerTrains(Long trimId, GuideInfo guideInfo) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> powerTrains = optionRepository.findOptionsByTrimIdAndType(trimId, OptionType.OPTIONAL, SelectiveCategory.POWER_TRAIN);

        List<Long> powerTrainIds = powerTrains.stream().map(Option::getId).collect(Collectors.toList());

        Map<Long, List<OptionImage>> powerTrainImagesGroup = getOptionImagesGroup(powerTrainIds);
        Map<Long, List<OptionDetail>> powerTrainDetailsGroup = getOptionDetailGroup(powerTrainIds);

        // 트림에 해당하는 파워 트레인 별 유사 사용자 수 비율
        Map<Long, Integer> similarityUsersRatio = getSimilarityUsersRatio(trimId, powerTrainIds, guideInfo);

        // 옵션 별 키워드 조회
        Map<Long, List<Keyword>> powerTrainIdKeywordGroup = keywordRepository
                .findByContainOptionIdsAndGroupKeywords(powerTrainIds);

        Map<Long, List<KeywordRate>> keywordRateGroup = findKeywordRateGroup(guideInfo, powerTrainIdKeywordGroup, trimId);

        return getSortedGuideOptionResponses(powerTrains, similarityUsersRatio, keywordRateGroup,
                powerTrainImagesGroup, powerTrainDetailsGroup);
    }

    private Map<Long, Integer> getSellRatio(Long trimId, List<Long> optionsIds, SelectiveCategory category) {
        Map<Long, Long> optionCounts = sellRepository
                .countOptionsByTrimIdAndContainOptionsIds(trimId, optionsIds, category);
        addMissingOptionIds(optionCounts, optionsIds);
        return calculateOptionRatios(optionCounts);
    }

    private Map<Long, Integer> getSimilarityUsersRatio(Long trimId, List<Long> optionsIds, GuideInfo guideInfo) {
        Map<Long, Long> powerTrainCounts = estimateRepository
                .countPowerTrainsSimilarityUsers(trimId, optionsIds, guideInfo);
        addMissingOptionIds(powerTrainCounts, optionsIds);
        return calculateOptionRatios(powerTrainCounts);
    }

    private Map<Long, Integer> calculateOptionRatios(Map<Long, Long> powerTrainCounts) {
        long total = powerTrainCounts.values().stream().mapToLong(Long::longValue).sum();

        Map<Long, Integer> optionRatios = new HashMap<>();
        for (Map.Entry<Long, Long> entry : powerTrainCounts.entrySet()) {
            Long powerTrainId = entry.getKey();
            Long count = entry.getValue();
            double ratio = (double) count * 100 / total;
            optionRatios.put(powerTrainId, Math.round((float) ratio));
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
        for (Long engineId : optionIds) {
            counts.putIfAbsent(engineId, 0L);
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
                                                              Map<Long, List<Keyword>> powerTrainIdKeywordGroup,
                                                              Long trimId) {
        Map<Long, List<KeywordRate>> keywordRateGroup = new HashMap<>();

        for (Long keywordId : guideInfo.getKeywordIds()) {
            // 사용자 키워드 우선순위 순으로 옵션이 해당 키워드를 갖는지 확인
            List<Long> optionIdsWithKeyword = powerTrainIdKeywordGroup.entrySet().stream()
                    .filter(entry -> entry.getValue().stream()
                            .anyMatch(keyword -> keyword.getId().equals(keywordId)))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // 겹치는 키워드가 없으면 비율을 조회 후 반환
            if (optionIdsWithKeyword.size() == 1) {
                Long selectedOptionId = optionIdsWithKeyword.get(0);
                int rate = estimateRepository.calculateRate(trimId, selectedOptionId, keywordId);
                Keyword keyword = keywordRepository.findById(keywordId)
                        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "키워드를 찾을 수 없습니다."));
                KeywordRate keywordRate = new KeywordRate(rate, keyword.getName());
                keywordRateGroup.put(selectedOptionId, List.of(keywordRate));
                return keywordRateGroup;
            }
        }
        return keywordRateGroup;
    }

    private List<FindGuideOptionResponse> getSortedGuideOptionResponses(List<Option> powerTrains,
                                                                        Map<Long, Integer> similarityUsersRatio,
                                                                        Map<Long, List<KeywordRate>> keywordRateGroup,
                                                                        Map<Long, List<OptionImage>> powerTrainImagesGroup,
                                                                        Map<Long, List<OptionDetail>> powerTrainDetailsGroup) {
        return powerTrains.stream()
                .map(powerTrain -> {
                    Integer ratio = similarityUsersRatio.get(powerTrain.getId());
                    boolean isSelected = determineSelection(keywordRateGroup, similarityUsersRatio, powerTrain.getId(), ratio);
                    return new FindGuideOptionResponse(
                            powerTrain,
                            isSelected,
                            ratio,
                            keywordRateGroup.getOrDefault(powerTrain.getId(), new ArrayList<>()),
                            powerTrainImagesGroup.getOrDefault(powerTrain.getId(), new ArrayList<>()),
                            powerTrainDetailsGroup.getOrDefault(powerTrain.getId(), new ArrayList<>())
                    );
                })
                .sorted(Comparator.comparingDouble((FindGuideOptionResponse response) -> -response.getRate()) // 비율 내림차순
                        .thenComparing((FindGuideOptionResponse response) -> response.isChecked() ? 0 : 1)) // true가 맨 처음
                .collect(Collectors.toList());
    }

    private boolean determineSelection(Map<Long, List<KeywordRate>> keywordRateGroup,
                                       Map<Long, Integer> similarityUsersRatio,
                                       Long powerTrainId,
                                       Integer ratio) {
        if (!keywordRateGroup.isEmpty()) {
            return !keywordRateGroup.getOrDefault(powerTrainId, Collections.emptyList()).isEmpty();
        }
        // 겹치는 키워드가 없으면 유사 사용자 비율이 높은 옵션을 선택
        boolean isMaxRate = ratio.equals(Collections.max(similarityUsersRatio.values()));
        if (isMaxRate) {
            similarityUsersRatio.put(powerTrainId, Integer.MAX_VALUE);
        }
        return isMaxRate;
    }
}
