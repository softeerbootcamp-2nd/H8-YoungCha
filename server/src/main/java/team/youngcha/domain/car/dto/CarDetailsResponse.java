package team.youngcha.domain.car.dto;

import lombok.Getter;
import team.youngcha.domain.trim.dto.TrimDetail;

import java.util.List;

@Getter
public class CarDetailsResponse {
    private String model;
    private List<TrimDetail> trims;

    public CarDetailsResponse(String model, List<TrimDetail> trims) {
        this.model = model;
        this.trims = trims;
    }
}
