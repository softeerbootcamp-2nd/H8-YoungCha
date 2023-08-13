package team.youngcha.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
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
import team.youngcha.domain.option.entity.OptionType;
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

    public List<FindSelfOptionResponse> findSelfPowerTrains(Long trimId) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> powerTrains = optionRepository.findPowerTrainsByTrimIdAndType(trimId, OptionType.OPTIONAL);

        List<Long> powerTrainIds = powerTrains.stream().map(Option::getId).collect(Collectors.toList());
        Map<Long, Integer> sellRatio = getSellRatio(trimId, powerTrainIds);
        Map<Long, List<OptionImage>> powerTrainImagesGroup = getOptionImagesGroup(powerTrainIds);
        Map<Long, List<OptionDetail>> powerTrainDetailsGroup = getOptionDetailGroup(powerTrainIds);

        return getSortedSelfOptionResponses(powerTrains, sellRatio, powerTrainImagesGroup, powerTrainDetailsGroup);
    }

    public List<FindGuideOptionResponse> findGuidePowerTrains(Long trimId, GuideInfo guideInfo) {
        trimRepository.findById(trimId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> powerTrains = optionRepository.findPowerTrainsByTrimIdAndType(trimId, OptionType.OPTIONAL);

        List<Long> powerTrainIds = powerTrains.stream().map(Option::getId).collect(Collectors.toList());

        Map<Long, List<OptionImage>> powerTrainImagesGroup = getOptionImagesGroup(powerTrainIds);
        Map<Long, List<OptionDetail>> powerTrainDetailsGroup = getOptionDetailGroup(powerTrainIds);

        // 트림에 해당하는 파워 트레인 별 유사 사용자 수 비율
        Map<Long, Integer> similarityUsersRatio = getSimilarityUsersRatio(trimId, powerTrainIds, guideInfo);

        // 옵션 별 키워드 조회
        Map<Long, List<Keyword>> powerTrainIdKeywordGroup = keywordRepository
                .findByContainOptionIdsAndGroupKeywords(powerTrainIds);

        for (Long keywordId : guideInfo.getKeywordIds()) {
            // 사용자 키워드 우선순위 순으로 옵션이 해당 키워드를 갖는지 확인
            List<Long> optionIds = new ArrayList<>();
            for (Map.Entry<Long, List<Keyword>> entry : powerTrainIdKeywordGroup.entrySet()) {
                // 옵션의 키워드에 사용자가 선택한 키워드가 있는 판별
                boolean containId = entry.getValue().stream()
                        .anyMatch(keyword -> keyword.getId().equals(keywordId));
                if (!containId) continue;
                optionIds.add(entry.getKey());
            }
            if (optionIds.size() != 1) {
                continue;
            }
            // 겹치는 키워드가 없으면 비율을 조회 후 반환
            Integer rate = estimateRepository.calculateRate(trimId, optionIds.get(0), keywordId);
            Keyword keyword = keywordRepository.findById(keywordId)
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "키워드를 찾을 수 없습니다."));
            KeywordRate keywordRate = new KeywordRate(rate, keyword.getName());
            Map<Long, List<KeywordRate>> keywordRateGroup = new HashMap<>();
            keywordRateGroup.put(optionIds.get(0), List.of(keywordRate));
            return getSortedGuideOptionResponses(powerTrains, similarityUsersRatio, keywordRateGroup,
                    powerTrainImagesGroup, powerTrainDetailsGroup);
        }
        // 모두 키워드가 없으면 유사 사용자 비율
        return getSortedGuideOptionResponses(powerTrains, similarityUsersRatio, new HashMap<>(),
                powerTrainImagesGroup, powerTrainDetailsGroup);
    }

    private Map<Long, Integer> getSellRatio(Long trimId, List<Long> optionsIds) {
        Map<Long, Long> powerTrainCounts = sellRepository
                .countPowerTrainByTrimIdAndContainPowerTrainIds(trimId, optionsIds);
        addMissingOptionIds(powerTrainCounts, optionsIds);
        return calculateOptionRatios(powerTrainCounts);
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

    private List<FindSelfOptionResponse> getSortedSelfOptionResponses(List<Option> powerTrains, Map<Long, Integer> sellRatio,
                                                                      Map<Long, List<OptionImage>> powerTrainImagesGroup,
                                                                      Map<Long, List<OptionDetail>> powerTrainDetailsGroup) {
        return powerTrains.stream()
                .map(powerTrain -> new FindSelfOptionResponse(powerTrain,
                        sellRatio.get(powerTrain.getId()),
                        powerTrainImagesGroup.getOrDefault(powerTrain.getId(), new ArrayList<>()),
                        powerTrainDetailsGroup.getOrDefault(powerTrain.getId(), new ArrayList<>())))
                .sorted(Comparator.comparingDouble(response -> -response.getRate()))
                .collect(Collectors.toList());
    }

    private List<FindGuideOptionResponse> getSortedGuideOptionResponses(List<Option> powerTrains,
                                                                        Map<Long, Integer> similarityUsersRatio,
                                                                        Map<Long, List<KeywordRate>> keywordRateGroup,
                                                                        Map<Long, List<OptionImage>> powerTrainImagesGroup,
                                                                        Map<Long, List<OptionDetail>> powerTrainDetailsGroup) {
        if (keywordRateGroup.isEmpty()) {
            return powerTrains.stream()
                    .map(powerTrain -> {
                        Integer ratio = similarityUsersRatio.get(powerTrain.getId());
                        boolean isMaxRatio = ratio.equals(Collections.max(similarityUsersRatio.values()));
                        if (isMaxRatio) { // 최대값이 하나만 되도록 해서 isChecked가 여러개 되는 것을 방지
                            similarityUsersRatio.put(powerTrain.getId(), Integer.MAX_VALUE);
                        }
                        return new FindGuideOptionResponse(
                                powerTrain,
                                isMaxRatio,
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
        return powerTrains.stream()
                .map(powerTrain -> {
                    Integer ratio = similarityUsersRatio.get(powerTrain.getId());
                    boolean isSelected = keywordRateGroup.getOrDefault(powerTrain.getId(), new ArrayList<>()).isEmpty();
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
}
