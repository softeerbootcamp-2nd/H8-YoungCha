package team.youngcha.common.enums;

import java.util.Arrays;

public enum Gender {
    MALE(0, "남성"),
    FEMALE(1, "여성"),
    NONE(2, "선택 안함");

    private final int type;
    private final String name;

    Gender(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Gender of(int type) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getType() == type)
                .findAny().orElseThrow(() -> new IllegalArgumentException("일치하는 gender가 없습니다."));
    }

    public int getType() {
        return type;
    }

    public String toKeyword() {
        return this.name;
    }
}
