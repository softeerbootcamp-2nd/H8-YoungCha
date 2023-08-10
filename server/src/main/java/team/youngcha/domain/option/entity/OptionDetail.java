package team.youngcha.domain.option.entity;

import lombok.Getter;

@Getter
public class OptionDetail {

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Long optionId;

    public OptionDetail(String name, String description, String imgUrl, Long optionId) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.optionId = optionId;
    }
}
