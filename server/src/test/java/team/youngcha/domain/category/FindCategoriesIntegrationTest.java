package team.youngcha.domain.category;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.category.dto.CategoryResponse;
import team.youngcha.domain.category.dto.FindCategoriesResponse;

import java.util.List;

@Sql("classpath:data/category.sql")
class FindCategoriesIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("옵션 카테고리의 목록을 조회한다")
    void findCategories() {
        //given
        String url = "/categories";

        FindCategoriesResponse expectedResponse = new FindCategoriesResponse(List.of(
                new CategoryResponse(1L, "파워 트레인"),
                new CategoryResponse(2L, "구동 방식"),
                new CategoryResponse(3L, "바디 타입"),
                new CategoryResponse(4L, "외장 색상"),
                new CategoryResponse(5L, "내장 색상"),
                new CategoryResponse(6L, "휠"),
                new CategoryResponse(7L, "시스템"),
                new CategoryResponse(8L, "온도 관리"),
                new CategoryResponse(9L, "외부 장치"),
                new CategoryResponse(10L, "내부 장치"),
                new CategoryResponse(11L, "성능"),
                new CategoryResponse(12L, "지능형 안전기술"),
                new CategoryResponse(13L, "안전"),
                new CategoryResponse(14L, "외관"),
                new CategoryResponse(15L, "내관"),
                new CategoryResponse(16L, "시트"),
                new CategoryResponse(17L, "편의"),
                new CategoryResponse(18L, "멀티미디어")));

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        SuccessResponse<FindCategoriesResponse> successResponse = response.body().as(new TypeRef<>() {
        });

        //then
        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData().getCategories().size()).isEqualTo(18);
        softAssertions.assertThat(successResponse.getData())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedResponse);
    }

}
