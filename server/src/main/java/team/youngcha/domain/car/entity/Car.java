package team.youngcha.domain.car.entity;

import lombok.Getter;

@Getter
public class Car {

    private Long id;
    private String name;

    public Car(String name) {
        this.name = name;
    }
}