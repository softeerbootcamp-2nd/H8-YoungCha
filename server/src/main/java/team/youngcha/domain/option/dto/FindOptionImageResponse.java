package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import team.youngcha.domain.option.entity.OptionImage;

@Getter
@Schema(description = "옵션 사진")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindOptionImageResponse {

    @Schema(description = "사진 주소")
    private String imgUrl;

    @Schema(description = "사진 타입 (0: 메인, 1: 서브, 2: 로고)")
    private int imgType;

    public FindOptionImageResponse(OptionImage optionImage) {
        this.imgUrl = optionImage.getImgUrl();
        this.imgType = optionImage.getImgType();
    }

    @Builder
    public FindOptionImageResponse(String imgUrl, int imgType) {
        this.imgUrl = imgUrl;
        this.imgType = imgType;
    }
}
