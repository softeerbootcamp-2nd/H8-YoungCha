package team.youngcha.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    MALE(0), FEMALE(1), NONE(2);

    private int type;

    Gender(int type) {
        this.type = type;
    }

    public static Gender of(int type) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getType() == type)
                .findAny().orElseThrow(() -> new IllegalArgumentException("일치하는 gender가 없습니다."));
    }
}
