package team.youngcha.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "옵션 카테고리 목록")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindCategoriesResponse {

    @Schema(description = "옵션 카테고리 목록")
    private List<CategoryResponse> categories;

    public FindCategoriesResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }
}
