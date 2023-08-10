package team.youngcha.domain.option.entity;

import lombok.Getter;

@Getter
public class OptionImage {

    private Long id;
    private String imgUrl;
    private int imgType;
    private Long optionId;

    public OptionImage(String imgUrl, int imgType, Long optionId) {
        this.imgUrl = imgUrl;
        this.imgType = imgType;
        this.optionId = optionId;
    }
}
