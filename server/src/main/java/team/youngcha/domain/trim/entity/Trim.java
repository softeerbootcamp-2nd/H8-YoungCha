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
}
