package team.youngcha.domain.option.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideInfo {

    private Gender gender;
    private AgeRange ageRange;
    private List<Long> keywordIds;

    @Builder
    public GuideInfo(Gender gender, AgeRange ageRange, List<Long> keywordIds) {
        this.gender = gender;
        this.ageRange = ageRange;
        this.keywordIds = keywordIds;
    }
}
