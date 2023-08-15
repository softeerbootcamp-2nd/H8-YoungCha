package team.youngcha.domain.trim.enums;

import lombok.Getter;

@Getter
public enum TrimOptionType {
    DEFAULT(0),
    SELECTIVE(1),
    MAIN(2);

    private int value;

    TrimOptionType(int value) {
        this.value = value;
    }
}
