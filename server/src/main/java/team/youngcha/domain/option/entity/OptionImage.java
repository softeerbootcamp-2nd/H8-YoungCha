package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionImage {

    private Long id;
    private String imgUrl;
    private int imgType;
    private Long optionId;
}
