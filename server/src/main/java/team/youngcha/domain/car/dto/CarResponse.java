package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.car.entity.Car;

@Schema(description = "자동차 모델 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarResponse {

    @Schema(description = "자동차 아이디")
    private Long id;

    @Schema(description = "모델명")
    private ModelName modelName;

    public CarResponse(Car car) {
        this.id = car.getId();
        this.modelName = new ModelName(car.getKoreanName(), car.getEnglishName());
    }
}
