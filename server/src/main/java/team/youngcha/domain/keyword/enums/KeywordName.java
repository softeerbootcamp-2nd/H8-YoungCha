package team.youngcha.domain.keyword.enums;

import lombok.Getter;

@Getter
public enum KeywordName {
    DRIVING_PERFORMANCE("주행력"),
    NOISE("소음"),
    EFFICIENCY("효율"),
    POWER("파워"),
    DESIGN("디자인"),
    VEHICLE_PROTECTION("차량 보호"),
    TEMPERATURE_CONTROL("온도 조절"),
    HEALTH("건강"),
    ADVANCED_TECHNOLOGY("신기술"),
    SAFETY("안전"),
    CAMPING("차박"),
    FAMILY_TRAVEL("가족 여행");

    private final String name;

    KeywordName(String name) {
        this.name = name;
    }
}
