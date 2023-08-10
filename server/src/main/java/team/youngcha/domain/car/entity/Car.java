package team.youngcha.domain.car.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Car {
    @Id
    private Long id;

    private String name;

    public Car(String name) {
        this.name = name;
    }
}