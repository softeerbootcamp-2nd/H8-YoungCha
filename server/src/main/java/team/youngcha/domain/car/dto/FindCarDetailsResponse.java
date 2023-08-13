package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import team.youngcha.domain.trim.dto.TrimDetail;

import java.util.List;

@Getter
@Schema(description = "자동차 상세정보")
public class FindCarDetailsResponse {

    @Schema(description = "이름")
    private final String model;

    @Schema(description = "트림 상세정보")
    private final List<TrimDetail> trims;

    public FindCarDetailsResponse(String model, List<TrimDetail> trims) {
        this.model = model;
        this.trims = trims;
    }
}
