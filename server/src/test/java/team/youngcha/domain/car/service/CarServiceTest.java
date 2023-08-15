package team.youngcha.domain.car.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.car.dto.FindCarDetailsResponse;
import team.youngcha.domain.car.entity.Car;
import team.youngcha.domain.car.repository.CarRepository;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.service.TrimService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @Mock
    TrimService trimService;

    @InjectMocks
    CarService carService;

    @Test
    @DisplayName("존재하지 않는 자동차 ID로 상세정보 조회 시, 404 NOT FOUND 예외가 발생한다")
    void findDetailsWithInvalidId() {
        //given
        given(carRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        CustomException customException = assertThrows(CustomException.class, () -> carService.findDetails(1L));

        //then
        assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(customException.getMessage()).isEqualTo("자동차 ID 조회 실패");
    }

    @Test
    @DisplayName("자동차의 상세정보가 없는 경우, 404 NOT FOUND 예외가 발생한다")
    void findDetailsNoResult() {
        //given
        given(carRepository.findById(anyLong())).willReturn(Optional.of(mock(Car.class)));
        given(carRepository.findDetails(anyLong())).willReturn(new ArrayList<>());

        //when
        CustomException customException = assertThrows(CustomException.class, () -> carService.findDetails(1L));

        //then
        assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(customException.getMessage()).isEqualTo("자동차 상세정보 조회 실패");
    }

    @Test
    @DisplayName("자동차 상세정보 조회 결과를 응답 클래스로 반환한다")
    void findDetails() {
        //given
        Car car = new Car(1L, "팰리세이드");

        List<CarDetails> carDetails = new ArrayList<>();
        carDetails.add(mock(CarDetails.class));

        List<TrimDetail> trimDetails = new ArrayList<>(Arrays.asList(
                mock(TrimDetail.class),
                mock(TrimDetail.class)));

        given(carRepository.findById(anyLong())).willReturn(Optional.of(car));
        given(carRepository.findDetails(anyLong())).willReturn(carDetails);
        given(trimService.extractTrimDetailsFromCarDetailsDtos(anyList())).willReturn(trimDetails);

        //when
        FindCarDetailsResponse findCarDetailsResponse = carService.findDetails(car.getId());

        //then
        assertThat(findCarDetailsResponse).isNotNull();
        assertThat(findCarDetailsResponse.getModel()).isEqualTo(car.getName());
        assertThat(findCarDetailsResponse.getTrims().size()).isEqualTo(2);
    }

}