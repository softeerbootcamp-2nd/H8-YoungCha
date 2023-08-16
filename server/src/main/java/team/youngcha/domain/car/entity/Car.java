package team.youngcha.domain.car.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car {

    private Long id;
    private String name;

    public Car(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}