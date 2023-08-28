package team.youngcha.domain.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "배경 이미지 주소")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrimBackgroundImgUrl {

    @Schema(description = "배경 이미지 주소")
    private String web;
    @Schema(description = "배경 이미지 주소")
    private String android;

    public TrimBackgroundImgUrl(String web, String android) {
        this.web = web;
        this.android = android;
    }
}
