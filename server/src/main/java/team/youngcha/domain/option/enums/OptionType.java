package team.youngcha.domain.option.enums;

import lombok.Getter;

@Getter
public enum OptionType {
    BASIC(0), REQUIRED(1), CORE(2), SELECTIVE(4);

    private final int type;

    OptionType(int type) {
        this.type = type;
    }
}
