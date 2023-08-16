package team.youngcha.domain.category.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.category.dto.CategoryResponse;
import team.youngcha.domain.category.dto.FindCategoriesResponse;
import team.youngcha.domain.category.entity.Category;
import team.youngcha.domain.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    @DisplayName("옵션의 카테고리 목록을 조회한다")
    void findAll() {
        //given
        List<Category> categories = List.of(
                new Category(1L, "파워 트레인"),
                new Category(2L, "구동 방식"),
                new Category(3L, "바디 타입"));

        List<CategoryResponse> categoryResponses = categories.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());

        FindCategoriesResponse expectedFindCategoriesResponse = new FindCategoriesResponse(categoryResponses);

        given(categoryRepository.findAll()).willReturn(categories);

        //when
        FindCategoriesResponse actualFindCategoriesResponse = categoryService.findCategories();

        //then
        Assertions.assertThat(actualFindCategoriesResponse.getCategories().size()).isEqualTo(3);

        Assertions.assertThat(actualFindCategoriesResponse)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedFindCategoriesResponse);
    }

    @Test
    @DisplayName("옵션 카테고리 목록 조회에 실패하면 NOT FOUND 예외가 발생한다")
    void foundNoCategory() {
        //given
        given(categoryRepository.findAll()).willReturn(new ArrayList<>());

        //when
        CustomException customException = assertThrows(CustomException.class, () ->
                categoryService.findCategories());

        //then
        Assertions.assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
