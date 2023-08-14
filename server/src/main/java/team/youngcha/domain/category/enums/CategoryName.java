package team.youngcha.domain.category.enums;

import lombok.Getter;

@Getter
public enum CategoryName {
    POWER_TRAIN("파워 트레인"),
    DRIVING_SYSTEM("구동 방식"),
    BODY_TYPE("바디 타입"),
    EXTERIOR_COLOR("외장 색상"),
    INTERIOR_COLOR("내장 색상"),
    WHEEL("휠"),
    SYSTEM("시스템"),
    TEMPERATURE_MANAGEMENT("온도 관리"),
    EXTERIOR_DEVICE("외부 장치"),
    INTERIOR_DEVICE("내부 장치"),
    PERFORMANCE("성능"),
    INTELLIGENT_SAFETY_TECHNOLOGY("지능형 안전기술"),
    SAFETY("안전"),
    EXTERIOR("외관"),
    INTERIOR("내관"),
    SEAT("시트"),
    CONVENIENCE("편의"),
    MULTIMEDIA("멀티미디어");

    private String value;

    CategoryName(String value) {
        this.value = value;
    }
}