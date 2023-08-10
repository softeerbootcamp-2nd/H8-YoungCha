package team.youngcha.domain.trim.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Trim {

    private Long id;
    private String name;
    private String imgUrl;
    private String backgroundImgUrl;
    private String hashtag;
    private int price;
    private String description;
    private Long carId;
}
