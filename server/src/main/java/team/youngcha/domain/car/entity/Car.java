package team.youngcha.domain.car.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car {

    private Long id;
    private String koreanName;
    private String englishName;

    public Car(Long id, String koreanName, String englishName) {
        this.id = id;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }
}