package team.youngcha.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AgeRange {

    AGE_20(2), AGE_30(3),
    AGE_40(4), AGE_50(5),
    AGE_60(6), AGE_70_OR_MORE(7);

    private final int range;

    AgeRange(int range) {
        this.range = range;
    }

    public static AgeRange of(int range) {
        return Arrays.stream(AgeRange.values())
                .filter(ageRange -> ageRange.getRange() == range)
                .findAny().orElseThrow(() -> new IllegalArgumentException("일치하는 age가 없습니다."));
    }
}
