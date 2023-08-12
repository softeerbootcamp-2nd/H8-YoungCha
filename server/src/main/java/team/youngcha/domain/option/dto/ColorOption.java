package team.youngcha.domain.option.dto;

import lombok.Getter;

@Getter
public class ColorOption {
    String name;
    String imgUrl;

    public ColorOption(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
