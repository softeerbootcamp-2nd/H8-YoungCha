package team.youngcha.domain.option.enums;

import lombok.Getter;

@Getter
public enum OptionImageType {
    MAIN(0),
    SUB(1),
    LOGO(2),
    ICON(3);

    private int value;

    OptionImageType(int value) {
        this.value = value;
    }
}
