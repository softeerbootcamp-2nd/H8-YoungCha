package team.youngcha.domain.option.enums;

import lombok.Getter;

@Getter
public enum OptionType {
    DEFAULT(0), REQUIRED(1), CORE(2), SELECTIVE(3);

    private final int type;

    OptionType(int type) {
        this.type = type;
    }
}
