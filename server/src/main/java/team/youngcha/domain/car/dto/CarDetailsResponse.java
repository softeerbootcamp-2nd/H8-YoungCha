package team.youngcha.domain.car.dto;

import lombok.Getter;
import team.youngcha.domain.trim.dto.TrimDetails;

import java.util.List;

@Getter
public class CarDetailsResponse {
    private String model;
    private List<TrimDetails> trims;

    public CarDetailsResponse(String model, List<TrimDetails> trims) {
        this.model = model;
        this.trims = trims;
    }
}
