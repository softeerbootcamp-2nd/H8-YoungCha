package team.youngcha.domain.car.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarDetails {

    private Long trimId;
    private String trimName;
    private String trimImgUrl;
    private String trimBackgroundImgUrl;
    private String trimHashTag;
    private Integer trimPrice;
    private String trimDescription;
    private Integer trimOptionType;
    private String optionName;
    private String optionCategoryName;
    private Integer optionImgType;
    private String optionImgUrl;

    public CarDetails(Long trimId, String trimName, String trimImgUrl, String trimBackgroundImgUrl, String trimHashTag, Integer trimPrice, String trimDescription, Integer trimOptionType, String optionName, String optionCategoryName, Integer optionImgType, String optionImgUrl) {
        this.trimId = trimId;
        this.trimName = trimName;
        this.trimImgUrl = trimImgUrl;
        this.trimBackgroundImgUrl = trimBackgroundImgUrl;
        this.trimHashTag = trimHashTag;
        this.trimPrice = trimPrice;
        this.trimDescription = trimDescription;
        this.trimOptionType = trimOptionType;
        this.optionName = optionName;
        this.optionCategoryName = optionCategoryName;
        this.optionImgType = optionImgType;
        this.optionImgUrl = optionImgUrl;
    }
}
