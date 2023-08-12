package team.youngcha.domain.option.dto;

import lombok.Getter;

@Getter
public class MainOption {
    String name;
    String imgUrl;

    public MainOption(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
