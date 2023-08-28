package team.youngcha.domain.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.category.dto.FindCategoriesResponse;
import team.youngcha.domain.category.service.CategoryService;


@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "옵션 카테고리 목록 조회", description = "옵션의 카테고리 목록을 조회합니다.")
    @GetMapping("/categories")
    public ResponseEntity<SuccessResponse<FindCategoriesResponse>> findCategories() {
        FindCategoriesResponse findCategoriesResponse = categoryService.findCategories();
        SuccessResponse<FindCategoriesResponse> successResponse = new SuccessResponse<>(findCategoriesResponse);
        return ResponseEntity.ok().body(successResponse);
    }
}
