package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "모델명 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelName {
    @Schema(description = "한글 이름")
    private String ko;
    @Schema(description = "영문 이름")
    private String en;

    public ModelName(String ko, String en) {
        this.ko = ko;
        this.en = en;
    }
}
