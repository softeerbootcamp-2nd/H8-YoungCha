package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import team.youngcha.domain.option.entity.OptionImage;

@Getter
@Schema(description = "옵션 사진")
public class FindOptionImageResponse {

    @Schema(description = "사진 주소")
    private String imgUrl;

    @Schema(description = "사진 타입 (0: 메인, 1: 서브, 2: 로고)")
    private int imgType;

    public FindOptionImageResponse(OptionImage optionImage) {
        this.imgUrl = optionImage.getImgUrl();
        this.imgType = optionImage.getImgType();
    }
}
