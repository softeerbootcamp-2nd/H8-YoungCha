package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "색상 옵션")
public class ColorOption {

    @Schema(description = "옵션 이름")
    String name;

    @Schema(description = "옵션 이미지 주소")
    String imgUrl;

    public ColorOption(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
