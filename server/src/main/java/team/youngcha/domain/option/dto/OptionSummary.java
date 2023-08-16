package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "옵션 개요")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionSummary {

    @Schema(description = "옵션 이름")
    String name;

    @Schema(description = "옵션 이미지 주소")
    String imgUrl;

    public OptionSummary(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
