package team.youngcha.domain.car.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.*;
import team.youngcha.domain.car.entity.Car;
import team.youngcha.domain.car.repository.CarDetailsRepository;
import team.youngcha.domain.car.repository.CarRepository;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.service.TrimService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    CarRepository carRepository;

    @Mock
    CarDetailsRepository carDetailsRepository;

    @Mock
    TrimService trimService;

    @Nested
    @DisplayName("자동차 상세정보 조회")
    class FindCarDetails {

        @Test
        @DisplayName("존재하지 않는 자동차 ID로 조회 시, 404 NOT FOUND 예외가 발생한다")
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
        @DisplayName("조회 결과가 없는 경우, NOT FOUND 예외가 발생한다")
        void findDetailsNoResult() {
            //given
            given(carRepository.findById(anyLong())).willReturn(Optional.of(mock(Car.class)));
            given(carDetailsRepository.findDetails(anyLong())).willReturn(new ArrayList<>());

            //when
            CustomException customException = assertThrows(CustomException.class, () -> carService.findDetails(1L));

            //then
            assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(customException.getMessage()).isEqualTo("자동차 상세정보 조회 실패");
        }

        @Test
        @DisplayName("조회 결과를 응답 DTO로 반환한다")
        void findDetails() {
            //given
            Car car = new Car(1L, "팰리세이드", "Palisade");

            List<CarDetails> carDetails = List.of(
                    new CarDetails(1L, "Guide Mode", "img1", "background1", "background1", "hashtag1", 500, null, null, null, null, null, null),
                    new CarDetails(2L, "Leblanc", "img2", "background2", "background2", "hashtag2", 1000, "description2", 3, "option", "category", 1, "optionImg"));

            List<TrimDetail> trimDetails = List.of(new TrimDetail(2L, "Leblanc",
                    new TrimBackgroundImgUrl("background2", "background2"),
                    "img2", "hashtag2", "description2", true, 1000));

            ModelName modelName = new ModelName("팰리세이드", "Palisade");

            GuideModeDetails guideModeDetails = new GuideModeDetails(new TrimBackgroundImgUrl("background1", "background1"), "hashtag1", 500);

            FindCarDetailsResponse expectedResponse = new FindCarDetailsResponse(modelName, guideModeDetails, trimDetails);

            given(carRepository.findById(anyLong())).willReturn(Optional.of(car));
            given(carDetailsRepository.findDetails(anyLong())).willReturn(carDetails);
            given(trimService.extractTrimDetails(anyList())).willReturn(trimDetails);

            //when
            FindCarDetailsResponse actualResponse = carService.findDetails(car.getId());

            //then
            assertThat(actualResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        }

    }

    @Nested
    @DisplayName("자동차 모델 목록 조회")
    class FindCars {

        @Test
        @DisplayName("조회 결과를 응답 DTO로 반환한다")
        void findCars() {
            //given
            List<Car> cars = List.of(
                    new Car(1L, "팰리세이드", "Palisade"),
                    new Car(2L, "소나타", "Sonata"),
                    new Car(3L, "아반떼", "Avante")
            );

            List<CarResponse> carResponses = cars.stream()
                    .map(CarResponse::new)
                    .collect(Collectors.toList());

            FindCarsResponse expected = new FindCarsResponse(carResponses);

            given(carRepository.findAll()).willReturn(cars);

            //when
            FindCarsResponse actual = carService.findCars();

            //then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("조회 결과가 없는 경우, NOT FOUND 예외가 발생한다")
        void foundNoCar() {
            //given
            given(carRepository.findAll()).willReturn(new ArrayList<>());

            //when
            CustomException customException = assertThrows(CustomException.class, () ->
                    carService.findCars());

            //then
            assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

}
