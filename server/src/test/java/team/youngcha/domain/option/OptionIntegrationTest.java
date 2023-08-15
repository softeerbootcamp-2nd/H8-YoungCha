package team.youngcha.domain.option;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.option.dto.*;

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
public class OptionIntegrationTest extends IntegrationTestBase {

    @Nested
    class SelfMode {

        @Test
        @DisplayName("트림의 파워 트레인 옵션을 셀프 모드로 조회한다.")
        void findSelfPowerTrain() {
            //given
            jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                    "values (1, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(2, 2, 1, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(3, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(4, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");

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

        @Test
        @DisplayName("트림의 구동 방식 옵션을 셀프 모드로 조회한다.")
        void findSelfDrivingSystem() {
            //given
            jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                    "values (1, 2, 1, 1, 3, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(2, 2, 1, 1, 4, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(3, 2, 1, 1, 3, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(4, 2, 1, 1, 4, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");

            //when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .when()
                    .get("/car-make/2/self/driving-system")
                    .then().log().all()
                    .extract();

            //then
            SuccessResponse<List<FindSelfOptionResponse>> successResponse = response.body().as(new TypeRef<>() {
            });

            FindOptionImageResponse wd2Image = FindOptionImageResponse.builder()
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_2wd_s.jpg")
                    .imgType(0).build();
            FindOptionImageResponse wd4Image = FindOptionImageResponse.builder()
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_htrac_s.jpg")
                    .imgType(0).build();

            FindOptionDetailResponse wd2Detail = FindOptionDetailResponse.builder()
                    .description("엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다.<br>" +
                            "차체가 가벼워 연료 효율이 높습니다.")
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_2wd_s.jpg")
                    .specs(List.of()).build();
            FindOptionDetailResponse wd4Detail = FindOptionDetailResponse.builder()
                    .description("전자식 상시 4륜 구동 시스템 입니다.<br>" +
                            "도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다.")
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_htrac_s.jpg")
                    .specs(List.of()).build();

            FindSelfOptionResponse optionResponse1 = FindSelfOptionResponse.builder()
                    .id(3L).rate(50).price(0).name("2WD")
                    .images(List.of(wd2Image))
                    .details(List.of(wd2Detail)).build();
            FindSelfOptionResponse optionResponse2 = FindSelfOptionResponse.builder()
                    .id(4L).rate(50).price(2370000).name("4WD")
                    .images(List.of(wd4Image))
                    .details(List.of(wd4Detail)).build();

            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(successResponse.getData()).hasSize(2);
            softAssertions.assertThat(successResponse.getData())
                    .usingRecursiveComparison()
                    .isEqualTo(List.of(optionResponse1, optionResponse2));
        }

        @Test
        @DisplayName("트림의 바디 타입 옵션을 셀프 모드로 조회한다.")
        void findSelfBodyType() {
            //given
            jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                    "values (1, 2, 1, 6, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(2, 2, 1, 5, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(3, 2, 1, 5, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                    "(4, 2, 1, 5, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");

            //when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .when()
                    .get("/car-make/2/self/body-type")
                    .then().log().all()
                    .extract();

            //then
            SuccessResponse<List<FindSelfOptionResponse>> successResponse = response.body().as(new TypeRef<>() {
            });

            FindOptionImageResponse seat7Image = FindOptionImageResponse.builder()
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_7seats_s.jpg")
                    .imgType(0).build();
            FindOptionImageResponse seat8Image = FindOptionImageResponse.builder()
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_8seats_s.jpg")
                    .imgType(0).build();

            FindOptionDetailResponse seat7Detail = FindOptionDetailResponse.builder()
                    .description("기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론," +
                            " 3열 탑승객의 승하차가 편리합니다.")
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_7seats_s.jpg")
                    .specs(List.of()).build();
            FindOptionDetailResponse seat8Detail = FindOptionDetailResponse.builder()
                    .description("1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다.")
                    .imgUrl("https://www.hyundai.com/contents/spec/guide/lx_8seats_s.jpg")
                    .specs(List.of()).build();

            FindSelfOptionResponse optionResponse1 = FindSelfOptionResponse.builder()
                    .id(5L).rate(75).price(0).name("7인승")
                    .images(List.of(seat7Image))
                    .details(List.of(seat7Detail)).build();
            FindSelfOptionResponse optionResponse2 = FindSelfOptionResponse.builder()
                    .id(6L).rate(25).price(0).name("8인승")
                    .images(List.of(seat8Image))
                    .details(List.of(seat8Detail)).build();

            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(successResponse.getData()).hasSize(2);
            softAssertions.assertThat(successResponse.getData())
                    .usingRecursiveComparison()
                    .isEqualTo(List.of(optionResponse1, optionResponse2));
        }
    }

    @Nested
    class GuideMode {

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
}
