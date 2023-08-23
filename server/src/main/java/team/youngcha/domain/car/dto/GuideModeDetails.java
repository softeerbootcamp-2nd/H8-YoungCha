package team.youngcha.domain.car.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideModeDetails {
    String backgroundImgUrl;
    String hashTag;
    int price;

    public GuideModeDetails(String backgroundImgUrl, String hashTag, int price) {
        this.backgroundImgUrl = backgroundImgUrl;
        this.hashTag = hashTag;
        this.price = price;
    }
}
