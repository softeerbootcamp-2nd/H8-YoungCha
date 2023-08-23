package team.youngcha.domain.car.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelName {
    private String ko;
    private String en;

    public ModelName(String ko, String en) {
        this.ko = ko;
        this.en = en;
    }
}
