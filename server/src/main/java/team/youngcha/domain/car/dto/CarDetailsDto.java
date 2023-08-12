package team.youngcha.domain.car.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CarDetailsDto {

    private Long carId;
    private String carName;
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
}
