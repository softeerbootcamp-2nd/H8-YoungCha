package team.youngcha.domain.trim.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TrimOption {

    private Long id;
    private int type;
    private Long trimId;
    private Long optionId;
}
