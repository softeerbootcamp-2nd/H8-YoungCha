package team.youngcha.domain.option.enums;

import lombok.Getter;

@Getter
public enum OptionType {
    BASIC(0), OPTIONAL(1), CORE(2);

    private final int type;

    OptionType(int type) {
        this.type = type;
    }
}
