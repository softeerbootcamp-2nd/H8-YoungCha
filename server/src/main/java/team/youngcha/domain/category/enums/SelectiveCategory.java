package team.youngcha.domain.category.enums;

import lombok.Getter;

@Getter
public enum SelectiveCategory {
    POWER_TRAIN("파워 트레인", "engine"),
    DRIVING_SYSTEM("구동 방식", "driving_system"),
    BODY_TYPE("바디 타입", "body_type"),
    EXTERIOR_COLOR("외장 색상", "exterior_color"),
    INTERIOR_COLOR("내장 색상", "interior_color"),
    WHEEL("휠", "wheel");

    private final String name;
    private final String column;

    SelectiveCategory(String name, String column){
        this.name = name;
        this.column = column;
    }
}
