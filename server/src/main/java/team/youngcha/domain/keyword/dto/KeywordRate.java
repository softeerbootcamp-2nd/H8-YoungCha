package team.youngcha.domain.keyword.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordRate {

    private int rate;
    private String name;

    @Builder
    public KeywordRate(int rate, String name) {
        this.rate = rate;
        this.name = name;
    }
}
