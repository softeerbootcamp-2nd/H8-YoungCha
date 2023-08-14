package team.youngcha.domain.car.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Car {

    private Long id;
    private String name;

    public Car(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}