package team.youngcha.domain.option.dto;

import lombok.Getter;
import team.youngcha.domain.option.entity.OptionImage;

@Getter
public class FindOptionImageResponse {

    private String imgUrl;
    private int imgType;

    public FindOptionImageResponse(OptionImage optionImage) {
        this.imgUrl = optionImage.getImgUrl();
        this.imgType = optionImage.getImgType();
    }
}
