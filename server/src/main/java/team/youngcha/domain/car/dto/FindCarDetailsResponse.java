package team.youngcha.domain.car.dto;

import lombok.Getter;
import team.youngcha.domain.trim.dto.TrimDetail;

import java.util.List;

@Getter
public class FindCarDetailsResponse {
    private final String model;
    private final List<TrimDetail> trims;

    public FindCarDetailsResponse(String model, List<TrimDetail> trims) {
        this.model = model;
        this.trims = trims;
    }
}
