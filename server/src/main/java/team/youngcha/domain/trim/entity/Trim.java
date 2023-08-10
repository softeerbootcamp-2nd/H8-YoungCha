package team.youngcha.domain.trim.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Trim {

    @Id
    private Long id;

    private String name;

    private String imgUrl;

    private String hashTag;

    private int price;

    private String description;

    public Trim(String name, String imgUrl, String hashTag, int price, String description) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.hashTag = hashTag;
        this.price = price;
        this.description = description;
    }
}
