package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.trim.dto.TrimDetail;

import java.util.List;

@Getter
@Schema(description = "자동차 상세정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindCarDetailsResponse {

    @Schema(description = "이름")
    private String model;

    @Schema(description = "트림 상세정보")
    private List<TrimDetail> trims;

    public FindCarDetailsResponse(String model, List<TrimDetail> trims) {
        this.model = model;
        this.trims = trims;
    }
}
