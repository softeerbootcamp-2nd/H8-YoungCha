package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionDetail {

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Long optionId;
}
