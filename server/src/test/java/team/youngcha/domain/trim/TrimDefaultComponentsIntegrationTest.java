package team.youngcha.domain.trim;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.option.dto.DefaultOptionSummary;
import team.youngcha.domain.trim.dto.FindTrimDefaultOptionsResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@DisplayName("트림 기본 품목 옵션들을 페이지 단위로 조회한다")
class TrimDefaultComponentsIntegrationTest extends IntegrationTestBase {

    @BeforeAll
    public static void setTestData(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data/car.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data/category.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data/options.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data/trim.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data/trim_options.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data/options_image.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Long trimId = 2L;
    Long categoryId = 1L;
    int pageSize = 3;
    int totalElements = 7;
    int totalPages = 3;

    ArrayList<DefaultOptionSummary> defaultOptionSummaries = new ArrayList<>(Arrays.asList(
            new DefaultOptionSummary("8단 자동변속기", 1L, "https://www.hyundai.com/contents/spec/LX24/8at_s.jpg"),
            new DefaultOptionSummary("ISG 시스템", 1L, "https://www.hyundai.com/contents/spec/LX24/isg_s.jpg"),
            new DefaultOptionSummary("통합주행모드", 1L, "https://www.hyundai.com/contents/spec/LX24/drivemode_s.jpg"),
            new DefaultOptionSummary("랙구동형 전동식 파워스티어링(R-MDPS)", 1L, "https://www.hyundai.com/contents/spec/LX24/rmdps_s.jpg"),
            new DefaultOptionSummary("전자식 변속버튼", 1L, "https://www.hyundai.com/contents/spec/LX24/sbw_s.jpg"),
            new DefaultOptionSummary("HTARC", 1L, "https://www.hyundai.com/contents/spec/LX24/htrac_s.jpg"),
            new DefaultOptionSummary("험로주행모드", 1L, "https://www.hyundai.com/contents/spec/LX24/tractionmode_s.jpg")));

    @Test
    @DisplayName("첫번째 페이지 조회 검증")
    void firstPage() {
        //given
        int targetPage = 1;

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .param("categoryId", categoryId)
                .param("size", pageSize)
                .param("page", targetPage)
                .pathParam("id", trimId)

                .when()
                .get("/trims/{id}/default-components")

                .then().log().all()
                .extract();

        //then
        SuccessResponse<FindTrimDefaultOptionsResponse> successResponse = response.body().as(new TypeRef<>() {
        });

        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        FindTrimDefaultOptionsResponse responseBodyData = successResponse.getData();

        softAssertions.assertThat(responseBodyData.getTrimId()).isEqualTo(trimId);
        softAssertions.assertThat(responseBodyData.isFirst()).isTrue();
        softAssertions.assertThat(responseBodyData.isLast()).isFalse();
        softAssertions.assertThat(responseBodyData.getTotalElements()).isEqualTo(totalElements);
        softAssertions.assertThat(responseBodyData.getTotalPages()).isEqualTo(totalPages);
        softAssertions.assertThat(responseBodyData.getContents().size()).isEqualTo(pageSize);
        softAssertions.assertThat(responseBodyData.getContents())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(defaultOptionSummaries.subList(0, 3));
    }

    @Test
    @DisplayName("두번째 페이지 조회 검증")
    void secondPage() {
        //given
        int targetPage = 2;

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .param("categoryId", categoryId)
                .param("size", pageSize)
                .param("page", targetPage)
                .pathParam("id", trimId)

                .when()
                .get("/trims/{id}/default-components")

                .then().log().all()
                .extract();

        //then
        SuccessResponse<FindTrimDefaultOptionsResponse> successResponse = response.body().as(new TypeRef<>() {
        });


        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        FindTrimDefaultOptionsResponse responseBodyData = successResponse.getData();

        softAssertions.assertThat(responseBodyData.getTrimId()).isEqualTo(trimId);
        softAssertions.assertThat(responseBodyData.isFirst()).isFalse();
        softAssertions.assertThat(responseBodyData.isLast()).isFalse();
        softAssertions.assertThat(responseBodyData.getTotalElements()).isEqualTo(totalElements);
        softAssertions.assertThat(responseBodyData.getTotalPages()).isEqualTo(totalPages);
        softAssertions.assertThat(responseBodyData.getContents().size()).isEqualTo(pageSize);
        softAssertions.assertThat(responseBodyData.getContents())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(defaultOptionSummaries.subList(3, 6));
    }

    @Test
    @DisplayName("마지막 페이지 조회 검증")
    void lastPage() {
        //given
        int targetPage = totalPages;
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .param("categoryId", categoryId)
                .param("size", pageSize)
                .param("page", targetPage)
                .pathParam("id", trimId)

                .when()
                .get("/trims/{id}/default-components")

                .then().log().all()
                .extract();

        //then
        SuccessResponse<FindTrimDefaultOptionsResponse> successResponse = response.body().as(new TypeRef<>() {
        });


        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        FindTrimDefaultOptionsResponse responseBodyData = successResponse.getData();

        softAssertions.assertThat(responseBodyData.getTrimId()).isEqualTo(trimId);
        softAssertions.assertThat(responseBodyData.isFirst()).isFalse();
        softAssertions.assertThat(responseBodyData.isLast()).isTrue();
        softAssertions.assertThat(responseBodyData.getTotalElements()).isEqualTo(totalElements);
        softAssertions.assertThat(responseBodyData.getTotalPages()).isEqualTo(totalPages);
        softAssertions.assertThat(responseBodyData.getContents().size()).isEqualTo(1);
        softAssertions.assertThat(responseBodyData.getContents())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(defaultOptionSummaries.subList(6, 7));
    }
}

