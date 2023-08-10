package team.youngcha.domain.trim.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
public class Trim {

    @Id
    private Long id;

    private String name;

    @Column("background_img_url")
    private String backgroundImgUrl;

    @Column("car_img_url")
    private String carImgUrl;

    private String hashTag;

    private int price;

    private String description;

    public Trim(String name, String backgroundImgUrl, String carImgUrl, String hashTag, int price, String description) {
        this.name = name;
        this.backgroundImgUrl = backgroundImgUrl;
        this.carImgUrl = carImgUrl;
        this.hashTag = hashTag;
        this.price = price;
        this.description = description;
    }
}
