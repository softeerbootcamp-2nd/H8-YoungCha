package team.youngcha.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.category.entity.Category;

@Schema(description = "옵션 카테고리 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResponse {

    @Schema(description = "카테고리 아이디")
    private Long id;

    @Schema(description = "카테고리 이름")
    private String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
