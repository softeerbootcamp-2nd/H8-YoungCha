package team.youngcha.domain.category.enums;

public enum BasicCategory {
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

    private String name;

    BasicCategory(String name) {
        this.name = name;
    }
}
