package team.youngcha.domain.option;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.option.dto.FindGuideOptionResponse;
import team.youngcha.domain.option.dto.FindOptionDetailResponse;
import team.youngcha.domain.option.dto.FindOptionImageResponse;
import team.youngcha.domain.option.dto.FindSpecResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Sql({"classpath:data/car.sql",
        "classpath:data/trim.sql",
        "classpath:data/category.sql",
        "classpath:data/options.sql",
        "classpath:data/trim_options.sql",
        "classpath:data/options_detail.sql",
        "classpath:data/options_image.sql",
        "classpath:data/keyword.sql",
        "classpath:data/options_keyword.sql",
        "classpath:data/spec.sql"})
public class GuideOptionIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    void setUp() {
        // 유사 사용자는 trim id, keyword 모두 포함, 나이대, 성별로 판단
        // 태그는 trim id, option id, keyword 중 하나 포함으로 판단
        jdbcTemplate.update("insert into estimate (id, trim_id, engine_id, body_type_id, " +
                "driving_system_id, exterior_color_id, interior_color_id, wheel_id, " +
                "keyword1_id, keyword2_id, keyword3_id, age_range, gender, create_date) " +
                "values (1, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 3, 1, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 1, 1, 1, 1, 1, 2, 1, 3, 3, 1, '2023-01-01 12:12:12')," +
                "(3, 2, 2, 1, 1, 1, 1, 1, 3, 2, 1, 3, 1, '2023-01-01 12:12:12')," +
                "(4, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 3, 1, '2023-01-01 12:12:12')," +
                "(5, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 3, 1, '2023-01-01 12:12:12')," +
                "(6, 2, 2, 1, 1, 1, 1, 1, 1, 2, 4, 3, 1, '2023-01-01 12:12:12')," + // 유사 사용자 x
                "(7, 2, 3, 1, 1, 1, 1, 1, 1, 2, 3, 3, 0, '2023-01-01 12:12:12')," + // 조회 되지 않아야 하는 데이터
                "(8, 3, 2, 1, 1, 1, 1, 1, 1, 2, 3, 3, 1, '2023-01-01 12:12:12')," + // 조회 되지 않아야 하는 데이터
                "(9, 2, 2, 1, 1, 1, 1, 1, 1, 3, 4, 2, 1, '2023-01-01 12:12:12')"); // 유사 사용자 x
    }

    @Test
    @DisplayName("트림의 파워 트레인 옵션을 가이드 모드로 조회한다.")
    void findSelfPowerTrain() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put("gender", Gender.FEMALE.getType());
        params.put("age", AgeRange.AGE_30.getRange());
        params.put("keyword1Id", 1L);
        params.put("keyword2Id", 2L);
        params.put("keyword3Id", 3L);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().params(params)
                .log().all()
                .when()
                .get("/car-make/2/guide/power-train")
                .then().log().all()
                .extract();

        //then
        SuccessResponse<List<FindGuideOptionResponse>> successResponse = response.body().as(new TypeRef<>() {
        });

        FindOptionImageResponse gasolineImage = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/spec/LX24/gasoline3.8.jpg")
                .imgType(0).build();
        FindOptionImageResponse dieselImage = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/spec/LX24/dieselengine2.2.jpg")
                .imgType(0).build();

        FindSpecResponse gasolineSpec1 = FindSpecResponse.builder()
                .name("최고출력")
                .description("295 ps / 6,000 rpm").build();
        FindSpecResponse gasolineSpec2 = FindSpecResponse.builder()
                .name("최대토크")
                .description("36.2 kgf-m / 5,200 rpm").build();
        FindOptionDetailResponse gasolineDetail = FindOptionDetailResponse.builder()
                .description("고효율의 3.8 가솔린 엔진으로 다이내믹한 주행 성능은 물론, 정속성까지 선사합니다.")
                .imgUrl("https://www.hyundai.com/contents/spec/LX24/gasoline3.8.jpg")
                .specs(List.of(gasolineSpec1, gasolineSpec2)).build();

        FindSpecResponse dieselSpec1 = FindSpecResponse.builder()
                .name("최고출력")
                .description("202 ps / 3,800 rpm").build();
        FindSpecResponse dieselSpec2 = FindSpecResponse.builder()
                .name("최대토크")
                .description("45.0 kgf-m / 1,750~2,750 rmp").build();
        FindOptionDetailResponse dieselDetail = FindOptionDetailResponse.builder()
                .description("강력한 토크와 탁월한 효율로 여유있는 파워와 높은 연비를 제공하는 디젤엔진입니다.")
                .imgUrl("https://www.hyundai.com/contents/spec/LX24/dieselengine2.2.jpg")
                .specs(List.of(dieselSpec1, dieselSpec2)).build();
        KeywordRate tag = new KeywordRate(83, "소음");

        FindGuideOptionResponse optionResponse1 = FindGuideOptionResponse.builder()
                .id(1L).rate(20).price(1480000)
                .name("디젤 2.2").checked(false)
                .tags(List.of())
                .images(List.of(dieselImage))
                .details(List.of(dieselDetail)).build();
        FindGuideOptionResponse optionResponse2 = FindGuideOptionResponse.builder()
                .id(2L).rate(80).price(0)
                .name("가솔린 3.8").checked(true)
                .tags(List.of(tag))
                .images(List.of(gasolineImage))
                .details(List.of(gasolineDetail)).build();


        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData()).hasSize(2);
        softAssertions.assertThat(successResponse.getData())
                .usingRecursiveComparison()
                .isEqualTo(List.of(optionResponse2, optionResponse1));
    }
}
