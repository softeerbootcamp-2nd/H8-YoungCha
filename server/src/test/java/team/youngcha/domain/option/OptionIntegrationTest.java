package team.youngcha.domain.option;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.option.dto.FindOptionDetailResponse;
import team.youngcha.domain.option.dto.FindOptionImageResponse;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
import team.youngcha.domain.option.dto.FindSpecResponse;

import java.util.List;

@Sql({"classpath:data/car.sql",
        "classpath:data/trim.sql",
        "classpath:data/category.sql",
        "classpath:data/options.sql",
        "classpath:data/trim_options.sql",
        "classpath:data/options_detail.sql",
        "classpath:data/options_image.sql",
        "classpath:data/spec.sql"})
public class OptionIntegrationTest extends IntegrationTestBase {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");
    }

    @Test
    @DisplayName("트림의 파워 트레인 옵션을 셀프 모드로 조회한다.")
    void findSelfPowerTrain() {
        //given
        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when()
                .get("/car-make/2/self/power-train")
                .then().log().all()
                .extract();

        //then
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = response.body().as(new TypeRef<>() {
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

        FindSelfOptionResponse optionResponse1 = FindSelfOptionResponse.builder()
                .id(2L).rate(75).price(0).name("가솔린 3.8")
                .images(List.of(gasolineImage))
                .details(List.of(gasolineDetail)).build();
        FindSelfOptionResponse optionResponse2 = FindSelfOptionResponse.builder()
                .id(1L).rate(25).price(1480000).name("디젤 2.2")
                .images(List.of(dieselImage))
                .details(List.of(dieselDetail)).build();

        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData()).hasSize(2);
        softAssertions.assertThat(successResponse.getData())
                .usingRecursiveComparison()
                .isEqualTo(List.of(optionResponse1, optionResponse2));
    }
}
