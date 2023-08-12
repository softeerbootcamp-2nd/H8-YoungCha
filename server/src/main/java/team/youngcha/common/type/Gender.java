package team.youngcha.common.type;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(0), FEMALE(1), NONE(2);

    private int type;

    Gender(int type) {
        this.type = type;
    }
}
