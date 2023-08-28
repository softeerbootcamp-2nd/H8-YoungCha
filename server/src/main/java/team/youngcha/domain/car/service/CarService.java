package team.youngcha.domain.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.*;
import team.youngcha.domain.car.entity.Car;
import team.youngcha.domain.car.repository.CarDetailsRepository;
import team.youngcha.domain.car.repository.CarRepository;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.service.TrimService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarDetailsRepository carDetailsRepository;
    private final TrimService trimService;

    public FindCarDetailsResponse findDetails(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "자동차 ID 조회 실패", "존재하지 자동차 ID 입니다."));

        List<CarDetails> carDetails = carDetailsRepository.findDetails(car.getId());
        if (carDetails.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "자동차 상세정보 조회 실패", "해당 자동차의 상세정보가 존재하지 않습니다.");
        }

        ModelName modelName = new ModelName(car.getKoreanName(), car.getEnglishName());

        GuideModeDetails guideModeDetails = extractGuideModeDetails(carDetails);

        List<TrimDetail> trimDetails = trimService.extractTrimDetails(carDetails);

        return new FindCarDetailsResponse(modelName, guideModeDetails, trimDetails);
    }

    private GuideModeDetails extractGuideModeDetails(List<CarDetails> carDetails) {
        for (CarDetails carDetail : carDetails) {
            if (carDetail.getTrimName().equals("Guide Mode")) {
                return new GuideModeDetails(
                        new TrimBackgroundImgUrl(carDetail.getTrimBackgroundImgUrlWeb(), carDetail.getTrimBackgroundImgUrlAndroid()),
                        carDetail.getTrimHashTag(),
                        carDetail.getTrimPrice());
            }
        }
        return new GuideModeDetails(new TrimBackgroundImgUrl("", ""), "", 0);
    }

    public FindCarsResponse findCars() {
        List<CarResponse> carResponses = carRepository.findAll()
                .stream()
                .map(CarResponse::new)
                .collect(Collectors.toList());

        if (carResponses.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "자동차 모델 목록 조회 실패", "조회 결과가 존재하지 않습니다.");
        }

        return new FindCarsResponse(carResponses);
    }
}
