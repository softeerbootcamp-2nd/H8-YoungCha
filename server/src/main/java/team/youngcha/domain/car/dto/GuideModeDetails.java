package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "가이드 모드 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideModeDetails {

    @Schema(description = "배경 이미지 주소")
    TrimBackgroundImgUrl backgroundImgUrl;
    @Schema(description = "해시태그")
    String hashTag;
    @Schema(description = "가격")
    int price;

    public GuideModeDetails(TrimBackgroundImgUrl backgroundImgUrl, String hashTag, int price) {
        this.backgroundImgUrl = backgroundImgUrl;
        this.hashTag = hashTag;
        this.price = price;
    }
}
