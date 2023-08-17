package team.youngcha.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.category.dto.CategoryResponse;
import team.youngcha.domain.category.dto.FindCategoriesResponse;
import team.youngcha.domain.category.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public FindCategoriesResponse findCategories() {
        List<CategoryResponse> categoryResponses = categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());

        if (categoryResponses.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "옵션 카테고리 목록 조회 실패");
        }

        return new FindCategoriesResponse(categoryResponses);
    }
}
