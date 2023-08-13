package team.youngcha.domain.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.CarDetailsDto;
import team.youngcha.domain.car.dto.CarDetailsResponse;
import team.youngcha.domain.car.entity.Car;
import team.youngcha.domain.car.repository.CarRepository;
import team.youngcha.domain.trim.dto.TrimDetails;
import team.youngcha.domain.trim.service.TrimService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final TrimService trimService;

    public CarDetailsResponse findDetails(Long carId) {
        Car car = carRepository.findById(carId);
        if (car == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "자동차 ID 조회 실패", "존재하지 자동차 ID 입니다.");
        }

        List<CarDetailsDto> carDetailsDtos = carRepository.findDetails(car.getId());
        if (carDetailsDtos.isEmpty()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "자동차 상세정보 조회 실패", "해당 자동차의 상세정보가 존재하지 않습니다.");
        }

        List<TrimDetails> trimDetails = trimService.extractTrimDetailsFromCarDetailsDtos(carDetailsDtos);

        return new CarDetailsResponse(car.getName(), trimDetails);
    }

}
