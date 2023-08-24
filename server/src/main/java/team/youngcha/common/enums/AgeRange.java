package team.youngcha.common.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Integer> getAges() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(this.range * 10 + i);
        }
        return list;
    }

    public int getMinAge() {
        return this.range * 10;
    }

    public int getMaxAge() {
        return this.range * 10 + 9;
    }

    public String toKeyword() {
        String keywordName = this.range * 10 + "대";
        if (this.range == 7) {
            keywordName += " 이상";
        }

        return keywordName;
    }
}
