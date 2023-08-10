package team.youngcha.domain.trim.entity;

import lombok.Getter;

@Getter
public class Trim {

    private Long id;
    private String name;
    private String backgroundImgUrl;
    private String carImgUrl;
    private String hashTag;
    private int price;
    private String description;
    private Long carId;

    public Trim(String name, String backgroundImgUrl, String carImgUrl, String hashTag, int price, String description, Long carId) {
        this.name = name;
        this.backgroundImgUrl = backgroundImgUrl;
        this.carImgUrl = carImgUrl;
        this.hashTag = hashTag;
        this.price = price;
        this.description = description;
        this.carId = carId;
    }
}
