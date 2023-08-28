package team.youngcha.domain.car.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.car.dto.FindCarDetailsResponse;
import team.youngcha.domain.car.dto.FindCarsResponse;
import team.youngcha.domain.car.service.CarService;

@Tag(name = "Car", description = "자동차 API")
@RequestMapping("/cars")
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @Operation(summary = "자동차 상세정보 조회", description = "자동차의 이름, 사진, 트림 종류, 가격, 옵션 등을 포함한 상세정보를 조회합니다.")
    @GetMapping("/{id}/details")
    public ResponseEntity<SuccessResponse<FindCarDetailsResponse>> findCarDetails(@PathVariable Long id) {
        FindCarDetailsResponse carDetails = carService.findDetails(id);
        SuccessResponse<FindCarDetailsResponse> successResponse = new SuccessResponse<>(carDetails);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "자동차 모델 목록 조회", description = "자동차 모델의 목록을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<SuccessResponse<FindCarsResponse>> findCars() {
        FindCarsResponse carsResponse = carService.findCars();
        SuccessResponse<FindCarsResponse> successResponse = new SuccessResponse<>(carsResponse);
        return ResponseEntity.ok().body(successResponse);
    }

}
