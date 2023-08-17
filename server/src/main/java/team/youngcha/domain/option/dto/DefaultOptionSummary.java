package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "기본 품목 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefaultOptionSummary {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "카테고리 아이디")
    private Long categoryId;

    @Schema(description = "이미지 주소")
    private String imgUrl;

    public DefaultOptionSummary(String name, Long categoryId, String imgUrl) {
        this.name = name;
        this.categoryId = categoryId;
        this.imgUrl = imgUrl;
    }
}
