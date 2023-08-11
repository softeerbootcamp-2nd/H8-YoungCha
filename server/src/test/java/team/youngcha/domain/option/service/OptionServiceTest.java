package team.youngcha.domain.option.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
import team.youngcha.domain.option.entity.*;
import team.youngcha.domain.option.repository.OptionDetailRepository;
import team.youngcha.domain.option.repository.OptionImageRepository;
import team.youngcha.domain.option.repository.OptionRepository;
import team.youngcha.domain.sell.repository.SellRepository;
import team.youngcha.domain.trim.entity.Trim;
import team.youngcha.domain.trim.repository.TrimRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    OptionService optionService;

    @Mock
    TrimRepository trimRepository;

    @Mock
    SellRepository sellRepository;

    @Mock
    OptionRepository optionRepository;

    @Mock
    OptionImageRepository optionImageRepository;

    @Mock
    OptionDetailRepository optionDetailRepository;


    List<Option> createOptions(int count, Long initId, Long categoryId) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Option option = new Option(initId, "name" + initId, Math.toIntExact(initId), "feedback" + initId, categoryId);
            options.add(option);
            initId++;
        }
        return options;
    }

    List<OptionImage> createOptionImages(int count, Long initId, Long optionId) {
        List<OptionImage> optionImages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            OptionImage optionImage = new OptionImage(initId, "imgUrl" + initId, 0, optionId);
            optionImages.add(optionImage);
            initId++;
        }
        return optionImages;
    }

    List<OptionDetail> createOptionDetails(int count, Long initId, Long optionId) {
        List<OptionDetail> optionDetails = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Spec spec = new Spec(1L, "name", "description", initId);
            OptionDetail optionDetail = new OptionDetail(initId, "name" + initId, "description" + initId, "imgUrl" + initId, optionId, List.of(spec));
            optionDetails.add(optionDetail);
            initId++;
        }
        return optionDetails;
    }

    @Test
    @DisplayName("power train을 self mode로 조회한다.")
    void findPowerTrainSelf() {
        Long trimId = 1L;
        List<Option> options = createOptions(3, 1L, 1L);
        List<OptionImage> optionImages = createOptionImages(2, 1L, options.get(0).getId());
        optionImages.addAll(createOptionImages(1, 3L, options.get(1).getId()));
        List<OptionDetail> optionDetails = createOptionDetails(1, 1L, options.get(0).getId());
        optionDetails.addAll(createOptionDetails(1, 2L, options.get(1).getId()));
        optionDetails.addAll(createOptionDetails(1, 3L, options.get(2).getId()));

        Map<Long, Long> powerTrainCounts = new HashMap<>();
        powerTrainCounts.put(options.get(1).getId(), 1L);
        powerTrainCounts.put(options.get(2).getId(), 2L);

        given(trimRepository.findById(trimId))
                .willReturn(Optional.of(mock(Trim.class)));
        given(optionRepository.findPowerTrainsByTrimIdAndType(trimId, OptionType.OPTIONAL))
                .willReturn(options);
        given(sellRepository.countPowerTrainByTrimIdAndContainPowerTrainIds(eq(trimId), anyList()))
                .willReturn(powerTrainCounts);
        given(optionImageRepository.findByContainOptionIds(anyList()))
                .willReturn(optionImages);
        given(optionDetailRepository.findWithSpecsByContainOptionIds(anyList()))
                .willReturn(optionDetails);

        //when
        List<FindSelfOptionResponse> powerTrainResponses = optionService.findSelfPowerTrains(trimId);

        //then
        FindSelfOptionResponse expected1 = new FindSelfOptionResponse(options.get(0), 0, optionImages.subList(0, 2), optionDetails.subList(0, 1));
        FindSelfOptionResponse expected2 = new FindSelfOptionResponse(options.get(1), 33, optionImages.subList(2, 3), optionDetails.subList(1, 2));
        FindSelfOptionResponse expected3 = new FindSelfOptionResponse(options.get(2), 67, List.of(), optionDetails.subList(2, 3));
        assertThat(powerTrainResponses).hasSize(3);
        assertThat(powerTrainResponses).usingRecursiveComparison()
                .isEqualTo(List.of(expected1, expected2, expected3));
    }

    @Test
    @DisplayName("trimId로 trim을 찾을 수 없으면 예외가 발생한다.")
    void trimNotFound() {
        //given
        Long trimId = 1L;
        given(trimRepository.findById(trimId)).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> optionService.findSelfPowerTrains(trimId));

        //then
        assertThat(throwable).isInstanceOf(CustomException.class);
        CustomException customException = (CustomException) throwable;
        assertThat(customException.getMessage()).isEqualTo("존재하지 않는 트림입니다.");
        assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}