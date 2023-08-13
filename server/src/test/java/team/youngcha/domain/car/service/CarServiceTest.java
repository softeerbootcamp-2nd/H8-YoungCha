package team.youngcha.domain.car.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.CarDetailsDto;
import team.youngcha.domain.car.dto.CarDetailsResponse;
import team.youngcha.domain.car.entity.Car;
import team.youngcha.domain.car.repository.CarRepository;
import team.youngcha.domain.trim.dto.TrimDetails;
import team.youngcha.domain.trim.service.TrimService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @Mock
    TrimService trimService;

    @InjectMocks
    CarService carService;

    @Test
    @DisplayName("존재하지 않는 자동차 ID로 상세정보 조회 시, 400 BAD REQUEST 예외가 발생한다")
    void findDetailsWithInvalidId() {
        //given
        Mockito.when(carRepository.findById(any(Long.class))).thenReturn(null);

        //when
        CustomException customException = assertThrows(CustomException.class, () -> carService.findDetails(1L));

        //then
        assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(customException.getMessage()).isEqualTo("자동차 ID 조회 실패");
    }

    @Test
    @DisplayName("자동차의 상세정보가 없는 경우, 500 INTERNAL SERVER ERROR 예외가 발생한다")
    void findDetailsNoResult() {
        //given
        Mockito.when(carRepository.findById(any(Long.class)))
                .thenReturn(Mockito.mock(Car.class));

        Mockito.when(carRepository.findDetails(any(Long.class)))
                .thenReturn(new ArrayList<>());

        //when
        CustomException customException = assertThrows(CustomException.class, () -> carService.findDetails(1L));

        //then
        assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(customException.getMessage()).isEqualTo("자동차 상세정보 조회 실패");
    }

    @Test
    @DisplayName("자동차 상세정보 조회 결과를 응답 클래스로 반환한다")
    void findDetails() {
        //given
        Car car = new Car(1L, "팰리세이드");

        List<CarDetailsDto> carDetailsDtos = new ArrayList<>();
        carDetailsDtos.add(Mockito.mock(CarDetailsDto.class));

        List<TrimDetails> trimDetails = new ArrayList<>(Arrays.asList(
                Mockito.mock(TrimDetails.class),
                Mockito.mock(TrimDetails.class)));

        Mockito.when(carRepository.findById(any(Long.class)))
                .thenReturn(car);

        Mockito.when(carRepository.findDetails(any(Long.class)))
                .thenReturn(carDetailsDtos);

        Mockito.when(trimService.extractTrimDetailsFromCarDetailsDtos(anyList()))
                .thenReturn(trimDetails);

        //when
        CarDetailsResponse carDetailsResponse = carService.findDetails(car.getId());

        //then
        assertThat(carDetailsResponse).isNotNull();
        assertThat(carDetailsResponse.getModel()).isEqualTo(car.getName());
        assertThat(carDetailsResponse.getTrims().size()).isEqualTo(2);
    }

}