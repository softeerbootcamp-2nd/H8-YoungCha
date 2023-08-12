package team.youngcha.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
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
    private final OptionImageRepository optionImageRepository;
    private final OptionDetailRepository optionDetailRepository;

    public List<FindSelfOptionResponse> findSelfPowerTrains(Long trimId) {
        trimRepository.findById(trimId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 트림입니다."));
        List<Option> powerTrains = optionRepository.findPowerTrainsByTrimIdAndType(trimId, OptionType.OPTIONAL);

        List<Long> powerTrainIds = powerTrains.stream().map(Option::getId).collect(Collectors.toList());
        Map<Long, Integer> sellRatio = getSellRatio(trimId, powerTrainIds);
        Map<Long, List<OptionImage>> powerTrainImagesGroup = getOptionImagesGroup(powerTrainIds);
        Map<Long, List<OptionDetail>> powerTrainDetailsGroup = getOptionDetailGroup(powerTrainIds);

        return getSortedSelfOptionResponses(powerTrains, sellRatio, powerTrainImagesGroup, powerTrainDetailsGroup);
    }

    private Map<Long, Integer> getSellRatio(Long trimId, List<Long> optionsIds) {
        Map<Long, Long> powerTrainCounts = sellRepository.countPowerTrainByTrimIdAndContainPowerTrainIds(trimId, optionsIds);
        addMissingOptionIds(powerTrainCounts, optionsIds);
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
}
