package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "기본 품목 정보")
@Getter
public class DefaultOptionSummary {

    @Schema(description = "이름")
    private final String name;

    @Schema(description = "카테고리 아이디")
    private final Long categoryId;

    @Schema(description = "이미지 주소")
    private final String imgUrl;

    public DefaultOptionSummary(String name, Long categoryId, String imgUrl) {
        this.name = name;
        this.categoryId = categoryId;
        this.imgUrl = imgUrl;
    }
}
