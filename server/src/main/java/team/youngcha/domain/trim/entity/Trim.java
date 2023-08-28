package team.youngcha.domain.trim.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trim {

    private Long id;
    private String name;
    private String imgUrl;
    private String backgroundImgUrlWeb;
    private String backgroundImgUrlAndroid;
    private String hashtag;
    private int price;
    private String description;
    private Long carId;

    @Builder
    public Trim(Long id, String name, String imgUrl, String backgroundImgUrlWeb, String backgroundImgUrlAndroid, String hashtag, int price, String description, Long carId) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.backgroundImgUrlWeb = backgroundImgUrlWeb;
        this.backgroundImgUrlAndroid = backgroundImgUrlAndroid;
        this.hashtag = hashtag;
        this.price = price;
        this.description = description;
        this.carId = carId;
    }
}
