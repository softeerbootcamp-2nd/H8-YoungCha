package team.youngcha.domain.option;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
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
        "classpath:data/keyword.sql",
        "classpath:data/options_keyword.sql",
        "classpath:data/spec.sql"})
public class SelfOptionIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("트림의 파워 트레인 옵션을 셀프 모드로 조회한다.")
    void findSelfPowerTrain() {
        //given
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, " +
                "exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");

        String url = "/car-make/2/self/power-train";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        //then
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
                .specs(List.of(gasolineSpec1, gasolineSpec2)).build();

        FindSpecResponse dieselSpec1 = FindSpecResponse.builder()
                .name("최고출력")
                .description("202 ps / 3,800 rpm").build();
        FindSpecResponse dieselSpec2 = FindSpecResponse.builder()
                .name("최대토크")
                .description("45.0 kgf-m / 1,750~2,750 rmp").build();
        FindOptionDetailResponse dieselDetail = FindOptionDetailResponse.builder()
                .description("강력한 토크와 탁월한 효율로 여유있는 파워와 높은 연비를 제공하는 디젤엔진입니다.")
                .specs(List.of(dieselSpec1, dieselSpec2)).build();

        FindSelfOptionResponse optionResponse1 = FindSelfOptionResponse.builder()
                .id(2L).categoryId(1L).rate(75).price(0).name("가솔린 3.8")
                .feedbackTitle("가솔린 엔진은 조용해요!")
                .feedbackDescription("조용한 주행을 원하신다면, 탁월한 선택입니다.")
                .images(List.of(gasolineImage))
                .details(List.of(gasolineDetail)).build();
        FindSelfOptionResponse optionResponse2 = FindSelfOptionResponse.builder()
                .id(1L).categoryId(1L).rate(25).price(1480000).name("디젤 2.2")
                .feedbackTitle("디젤 엔진은 효율이 좋아요!")
                .feedbackDescription("효율을 중시한다면, 탁월한 선택입니다.")
                .images(List.of(dieselImage))
                .details(List.of(dieselDetail)).build();

        assertResponseAndExpected(response, List.of(optionResponse1, optionResponse2));
    }

    @Test
    @DisplayName("트림의 구동 방식 옵션을 셀프 모드로 조회한다.")
    void findSelfDrivingSystem() {
        //given
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, " +
                "exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 2, 1, 1, 3, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 1, 4, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 1, 1, 3, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 1, 1, 4, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");

        String url = "/car-make/2/self/driving-system";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        //then
        FindOptionImageResponse wd2Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/2wd.png")
                .imgType(0).build();
        FindOptionImageResponse wd4Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/4wd.png")
                .imgType(0).build();

        FindOptionDetailResponse wd2Detail = FindOptionDetailResponse.builder()
                .description("엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다.<br>" +
                        "차체가 가벼워 연료 효율이 높습니다.")
                .specs(List.of()).build();
        FindOptionDetailResponse wd4Detail = FindOptionDetailResponse.builder()
                .description("전자식 상시 4륜 구동 시스템 입니다.<br>" +
                        "도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다.")
                .specs(List.of()).build();

        FindSelfOptionResponse optionResponse1 = FindSelfOptionResponse.builder()
                .id(3L).categoryId(2L).rate(50).price(0).name("2WD")
                .feedbackTitle("2WD는 효율이 좋아요!")
                .feedbackDescription("효율을 중시한다면, 탁월한 선택입니다.")
                .images(List.of(wd2Image))
                .details(List.of(wd2Detail)).build();
        FindSelfOptionResponse optionResponse2 = FindSelfOptionResponse.builder()
                .id(4L).categoryId(2L).rate(50).price(2370000).name("4WD")
                .feedbackTitle("4WD는 파워풀해요!")
                .feedbackDescription("힘있는 주행을 원하신다면, 탁월한 선택입니다.")
                .images(List.of(wd4Image))
                .details(List.of(wd4Detail)).build();

        assertResponseAndExpected(response, List.of(optionResponse1, optionResponse2));
    }

    @Test
    @DisplayName("트림의 바디 타입 옵션을 셀프 모드로 조회한다.")
    void findSelfBodyType() {
        //given
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, " +
                "exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 2, 1, 6, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 5, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 1, 5, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 1, 5, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");

        String url = "/car-make/2/self/body-type";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        //then
        FindOptionImageResponse seat7Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/7seat.png")
                .imgType(0).build();
        FindOptionImageResponse seat8Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/8seat.png")
                .imgType(0).build();

        FindOptionDetailResponse seat7Detail = FindOptionDetailResponse.builder()
                .description("기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론," +
                        " 3열 탑승객의 승하차가 편리합니다.")
                .specs(List.of()).build();
        FindOptionDetailResponse seat8Detail = FindOptionDetailResponse.builder()
                .description("1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다.")
                .specs(List.of()).build();

        FindSelfOptionResponse optionResponse1 = FindSelfOptionResponse.builder()
                .id(5L).categoryId(3L).rate(75)
                .price(0).name("7인승")
                .feedbackTitle("통로가 있어 쾌적해요!")
                .feedbackDescription("쾌적함을 원하신다면, 탁월한 선택입니다.")
                .images(List.of(seat7Image))
                .details(List.of(seat7Detail)).build();
        FindSelfOptionResponse optionResponse2 = FindSelfOptionResponse.builder()
                .id(6L).categoryId(3L).rate(25)
                .price(0).name("8인승")
                .feedbackTitle("많은 사람이 탑승할 수 있어요!")
                .feedbackDescription("공간 활용을 원하신다면, 탁월한 선택입니다.")
                .images(List.of(seat8Image))
                .details(List.of(seat8Detail)).build();

        assertResponseAndExpected(response, List.of(optionResponse1, optionResponse2));
    }

    @Test
    @DisplayName("트림의 외장 색상 옵션을 셀프 모드로 조회한다.")
    void findSelfExteriorColor() {
        //given
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, " +
                "exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 2, 1, 1, 1, 7, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 1, 1, 10, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 1, 1, 1, 10, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 1, 1, 1, 9, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(5, 2, 1, 1, 1, 9, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(6, 2, 1, 1, 1, 11, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(7, 2, 1, 1, 1, 12, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(8, 2, 1, 1, 1, 12, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(9, 2, 1, 1, 1, 12, 1, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(10, 2, 1, 1, 1, 12, 1, 1, 50, 0, '2023-01-01 12:12:12')");

        String url = "/car-make/2/self/exterior-color";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        //then
        FindOptionImageResponse creamyWhiteImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/exterior/WC9/colorchip-exterior.png")
                .imgType(1).build();
        FindSelfOptionResponse creamyWhite = FindSelfOptionResponse.builder()
                .id(12L).categoryId(4L).rate(40).price(100000)
                .name("크리미 화이트 펄")
                .feedbackTitle("크리미 화이트 펄은 우아한 분위기에요!")
                .feedbackDescription("밝은 화이트(흰색)의 외장색상으로, 차량에 깨끗하고 우아한 느낌을 줍니다.")
                .images(List.of(creamyWhiteImg)).details(List.of()).build();

        FindOptionImageResponse moonLightImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/exterior/UB7/colorchip-exterior.png")
                .imgType(1).build();
        FindSelfOptionResponse moonLight = FindSelfOptionResponse.builder()
                .id(9L).categoryId(4L).rate(20).price(0)
                .name("문라이트 블루 펄")
                .feedbackTitle("문라이트 블루 펄은 활기찬 분위기에요!")
                .feedbackDescription("밝은 파란색의 외장색상으로, 차량에 상쾌하고 활기찬 느낌을 줍니다.")
                .images(List.of(moonLightImg)).details(List.of()).build();

        FindOptionImageResponse gaiaBrownImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/exterior/D2S/colorchip-exterior.png")
                .imgType(1).build();
        FindSelfOptionResponse gaiaBrown = FindSelfOptionResponse.builder()
                .id(10L).categoryId(4L).rate(20).price(0)
                .name("가이아 브라운 펄")
                .feedbackTitle("가이아 브라운 펄은 고급스러워요!")
                .feedbackDescription("브라운 계열의 외장색상으로, 차량에 고급스러움과 차분한 분위기를 부여합니다.")
                .images(List.of(gaiaBrownImg)).details(List.of()).build();

        FindOptionImageResponse abyssBlackImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/exterior/A2B/colorchip-exterior.png")
                .imgType(1).build();
        FindSelfOptionResponse abyssBlack = FindSelfOptionResponse.builder()
                .id(7L).categoryId(4L).rate(10).price(0)
                .name("어비스 블랙펄")
                .feedbackTitle("어비스 블랙펄은 고급스러워요!")
                .feedbackDescription("깊은 검정색의 외장색상으로, 차량에 고급스러움과 우아함을 더해줍니다.")
                .images(List.of(abyssBlackImg)).details(List.of()).build();

        FindOptionImageResponse graphiteGrayImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/exterior/P7V/colorchip-exterior.png")
                .imgType(1).build();
        FindSelfOptionResponse graphiteGray = FindSelfOptionResponse.builder()
                .id(11L).categoryId(4L).rate(10).price(0)
                .name("그라파이트 그레이 메탈릭")
                .feedbackTitle("그라파이트 그레이 메탈릭은 세련된 느낌을 줘요!")
                .feedbackDescription("회색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 부여합니다.")
                .images(List.of(graphiteGrayImg)).details(List.of()).build();

        FindOptionImageResponse shimmeringSilverImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/exterior/R2T/colorchip-exterior.png")
                .imgType(1).build();
        FindSelfOptionResponse shimmeringSilver = FindSelfOptionResponse.builder()
                .id(8L).categoryId(4L).rate(0).price(0)
                .name("쉬머링 실버 메탈릭")
                .feedbackTitle("쉬머링 실버 메탈릭은 현대적이에요!")
                .feedbackDescription("은색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 더해줍니다.")
                .images(List.of(shimmeringSilverImg)).details(List.of()).build();

        assertResponseAndExpected(response, List.of(creamyWhite, moonLight,
                gaiaBrown, abyssBlack, graphiteGray, shimmeringSilver));
    }

    @Test
    @DisplayName("트림의 내장 색상 옵션을 셀프 모드로 조회한다.")
    void findSelfInteriorColor() {
        //given
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, " +
                "exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 2, 1, 1, 1, 1, 13, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(2, 2, 1, 1, 1, 1, 14, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 1, 1, 1, 1, 13, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 1, 1, 1, 1, 14, 1, 50, 0, '2023-01-01 12:12:12')," +
                "(5, 2, 1, 1, 1, 1, 13, 1, 50, 0, '2023-01-01 12:12:12')");

        String url = "/car-make/2/self/interior-color?exteriorColorId=7";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        //then
        FindOptionImageResponse blackBigImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/interior/I49/img-interior.png")
                .imgType(0).build();
        FindOptionImageResponse blackSmallImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/interior/I49/colorchip-interior.png")
                .imgType(1).build();
        FindSelfOptionResponse black = FindSelfOptionResponse.builder()
                .id(13L).categoryId(5L).rate(60).price(0)
                .name("퀄팅천연(블랙)")
                .feedbackTitle("블랙 컬러는 클래식한 분위기에요!")
                .feedbackDescription("클래식한 분위기를 원하신다면, 탁월한 선택입니다.")
                .images(List.of(blackBigImg, blackSmallImg)).details(List.of()).build();

        FindOptionImageResponse grayBigImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/interior/YJY/img-interior.png")
                .imgType(0).build();
        FindOptionImageResponse graySmallImg = FindOptionImageResponse.builder()
                .imgUrl("https://www.hyundai.com/contents/vr360/LX06/interior/YJY/colorchip-interior.png")
                .imgType(1).build();
        FindSelfOptionResponse gray = FindSelfOptionResponse.builder()
                .id(14L).categoryId(5L).rate(40).price(0)
                .name("쿨그레이")
                .feedbackTitle("쿨그레이는 환한 분위기에요!")
                .feedbackDescription("환한 분위기를 원하신다면, 탁월한 선택입니다.")
                .images(List.of(grayBigImg, graySmallImg)).details(List.of()).build();

        assertResponseAndExpected(response, List.of(black, gray));
    }

    private void assertResponseAndExpected(ExtractableResponse<Response> response,
                                           List<FindSelfOptionResponse> expectedResponses) {
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = response.body().as(new TypeRef<>() {
        });
        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData()).hasSize(expectedResponses.size());
        softAssertions.assertThat(successResponse.getData())
                .usingRecursiveComparison()
                .isEqualTo(expectedResponses);
    }
}
