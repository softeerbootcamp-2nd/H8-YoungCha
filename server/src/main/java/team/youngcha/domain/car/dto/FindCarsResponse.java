package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "자동차 모델 목록")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindCarsResponse {

    @Schema(description = "자동차 모델 목록")
    private List<CarResponse> cars;

    public FindCarsResponse(List<CarResponse> cars) {
        this.cars = cars;
    }
}
