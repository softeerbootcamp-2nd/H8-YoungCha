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
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.option.dto.FindGuideOptionResponse;
import team.youngcha.domain.option.dto.FindOptionDetailResponse;
import team.youngcha.domain.option.dto.FindOptionImageResponse;
import team.youngcha.domain.option.dto.FindSpecResponse;

import java.util.ArrayList;
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
        "classpath:data/spec.sql",
        "classpath:data/sell.sql",
        "classpath:data/estimate.sql"})
public class GuideOptionIntegrationTest extends IntegrationTestBase {

    Map<String, Object> params = new HashMap<>() {{
        put("gender", Gender.FEMALE.getType());
        put("age", AgeRange.AGE_30.getRange());
        put("keyword1Id", 1L);
        put("keyword2Id", 2L);
        put("keyword3Id", 3L);
    }};

    @Test
    @DisplayName("트림의 파워 트레인을 가이드 모드로 조회한다.")
    void findSelfPowerTrain() {
        //given
        String url = "/car-make/2/guide/power-train";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, params);

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
        KeywordRate tag = new KeywordRate(80, "소음");

        FindGuideOptionResponse optionResponse1 = FindGuideOptionResponse.builder()
                .id(1L).categoryId(1L)
                .rate(20).price(1_480_000)
                .name("디젤 2.2").checked(false)
                .feedbackTitle("디젤 엔진은 효율이 좋아요!")
                .feedbackDescription("효율을 중시한다면, 탁월한 선택입니다.")
                .tags(List.of())
                .images(List.of(dieselImage))
                .details(List.of(dieselDetail)).build();
        FindGuideOptionResponse optionResponse2 = FindGuideOptionResponse.builder()
                .id(2L).categoryId(1L)
                .rate(80).price(0)
                .feedbackTitle("가솔린 엔진은 조용해요!")
                .feedbackDescription("조용한 주행을 원하신다면, 탁월한 선택입니다.")
                .name("가솔린 3.8").checked(true)
                .tags(List.of(tag))
                .images(List.of(gasolineImage))
                .details(List.of(gasolineDetail)).build();

        assertResponseAndExpected(response, List.of(optionResponse2, optionResponse1));
    }

    @Test
    @DisplayName("트림의 구동 방식을 가이드 모드로 조회한다.")
    void findSelfDrivingSystem() {
        //given
        String url = "/car-make/2/guide/driving-system";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, params);

        //then
        FindOptionImageResponse wd2Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/2wd.png")
                .imgType(0).build();
        FindOptionDetailResponse wd2Detail = FindOptionDetailResponse.builder()
                .description("엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다.<br>차체가 가벼워 연료 효율이 높습니다.")
                .specs(List.of()).build();
        FindGuideOptionResponse optionResponse1 = FindGuideOptionResponse.builder()
                .id(3L).categoryId(2L)
                .rate(80).price(0)
                .feedbackTitle("2WD는 효율이 좋아요!")
                .feedbackDescription("효율을 중시한다면, 탁월한 선택입니다.")
                .name("2WD").checked(false)
                .tags(List.of())
                .images(List.of(wd2Image))
                .details(List.of(wd2Detail)).build();

        FindOptionImageResponse wd4Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/4wd.png")
                .imgType(0).build();
        FindOptionDetailResponse wd4Detail = FindOptionDetailResponse.builder()
                .description("전자식 상시 4륜 구동 시스템 입니다.<br>도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다.")
                .specs(List.of()).build();
        FindGuideOptionResponse optionResponse2 = FindGuideOptionResponse.builder()
                .id(4L).categoryId(2L)
                .rate(20).price(2_370_000)
                .feedbackTitle("4WD는 파워풀해요!")
                .feedbackDescription("힘있는 주행을 원하신다면, 탁월한 선택입니다.")
                .name("4WD").checked(true)
                .tags(List.of(new KeywordRate(60, "주행력")))
                .images(List.of(wd4Image))
                .details(List.of(wd4Detail)).build();

        assertResponseAndExpected(response, List.of(optionResponse2, optionResponse1));
    }

    @Test
    @DisplayName("트림의 바디 타입을 가이드 모드로 조회한다.")
    void findSelfBodyType() {
        //given
        String url = "/car-make/2/guide/body-type";

        //when
        ExtractableResponse<Response> response = callEndpoint(url, params);

        //then
        FindOptionImageResponse seat7Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/7seat.png")
                .imgType(0).build();
        FindOptionDetailResponse seat7Detail = FindOptionDetailResponse.builder()
                .description("기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, " +
                        "3열 탑승객의 승하차가 편리합니다.")
                .specs(List.of()).build();
        FindGuideOptionResponse optionResponse1 = FindGuideOptionResponse.builder()
                .id(5L).categoryId(3L)
                .rate(100).price(0)
                .feedbackTitle("통로가 있어 쾌적해요!")
                .feedbackDescription("쾌적함을 원하신다면, 탁월한 선택입니다.")
                .name("7인승").checked(true)
                .tags(List.of())
                .images(List.of(seat7Image))
                .details(List.of(seat7Detail)).build();

        FindOptionImageResponse seat8Image = FindOptionImageResponse.builder()
                .imgUrl("https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/8seat.png")
                .imgType(0).build();
        FindOptionDetailResponse seat8Detail = FindOptionDetailResponse.builder()
                .description("1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다.")
                .specs(List.of()).build();
        FindGuideOptionResponse optionResponse2 = FindGuideOptionResponse.builder()
                .id(6L).categoryId(3L)
                .rate(0).price(0)
                .feedbackTitle("많은 사람이 탑승할 수 있어요!")
                .feedbackDescription("공간 활용을 원하신다면, 탁월한 선택입니다.")
                .name("8인승").checked(false)
                .tags(List.of())
                .images(List.of(seat8Image))
                .details(List.of(seat8Detail)).build();

        assertResponseAndExpected(response, List.of(optionResponse1, optionResponse2));
    }

    @Test
    @DisplayName("가이드 모드 외장색상 조회를 검증한다.")
    void findExteriorColor() {
        //given
        String url = "/car-make/2/guide/exterior-color";

        FindGuideOptionResponse findGuideOptionResponse1 = new FindGuideOptionResponse(9L, 4L, true, 0, 0,
                "문라이트 블루 펄", "문라이트 블루 펄은 활기찬 분위기에요!", "밝은 파란색의 외장색상으로, 차량에 상쾌하고 활기찬 느낌을 줍니다.",
                List.of(new KeywordRate(50, "30대"), new KeywordRate(40, "여성")), new ArrayList<>(),
                List.of(new FindOptionImageResponse("https://www.hyundai.com/contents/vr360/LX06/exterior/UB7/colorchip-exterior.png", 1)));

        FindGuideOptionResponse findGuideOptionResponse2 = new FindGuideOptionResponse(7L, 4L, false, 0, 0,
                "어비스 블랙펄", "어비스 블랙펄은 고급스러워요!", "깊은 검정색의 외장색상으로, 차량에 고급스러움과 우아함을 더해줍니다.",
                List.of(new KeywordRate(0, "30대"), new KeywordRate(0, "여성")), new ArrayList<>(),
                List.of(new FindOptionImageResponse("https://www.hyundai.com/contents/vr360/LX06/exterior/A2B/colorchip-exterior.png", 1)));

        FindGuideOptionResponse findGuideOptionResponse3 = new FindGuideOptionResponse(8L, 4L, false, 0, 0,
                "쉬머링 실버 메탈릭", "쉬머링 실버 메탈릭은 현대적이에요!", "은색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 더해줍니다.",
                List.of(new KeywordRate(0, "30대"), new KeywordRate(20, "여성")), new ArrayList<>(),
                List.of(new FindOptionImageResponse("https://www.hyundai.com/contents/vr360/LX06/exterior/R2T/colorchip-exterior.png", 1)));

        FindGuideOptionResponse findGuideOptionResponse4 = new FindGuideOptionResponse(10L, 4L, false, 0, 0,
                "가이아 브라운 펄", "가이아 브라운 펄은 고급스러워요!", "브라운 계열의 외장색상으로, 차량에 고급스러움과 차분한 분위기를 부여합니다.",
                List.of(new KeywordRate(0, "30대"), new KeywordRate(0, "여성")), new ArrayList<>(),
                List.of(new FindOptionImageResponse("https://www.hyundai.com/contents/vr360/LX06/exterior/D2S/colorchip-exterior.png", 1)));

        FindGuideOptionResponse findGuideOptionResponse5 = new FindGuideOptionResponse(11L, 4L, false, 0, 0,
                "그라파이트 그레이 메탈릭", "그라파이트 그레이 메탈릭은 세련된 느낌을 줘요!", "회색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 부여합니다.",
                List.of(new KeywordRate(0, "30대"), new KeywordRate(0, "여성")), new ArrayList<>(),
                List.of(new FindOptionImageResponse("https://www.hyundai.com/contents/vr360/LX06/exterior/P7V/colorchip-exterior.png", 1)));

        FindGuideOptionResponse findGuideOptionResponse6 = new FindGuideOptionResponse(12L, 4L, false, 0, 100000,
                "크리미 화이트 펄", "크리미 화이트 펄은 우아한 분위기에요!", "밝은 화이트(흰색)의 외장색상으로, 차량에 깨끗하고 우아한 느낌을 줍니다.",
                List.of(new KeywordRate(50, "30대"), new KeywordRate(40, "여성")), new ArrayList<>(),
                List.of(new FindOptionImageResponse("https://www.hyundai.com/contents/vr360/LX06/exterior/WC9/colorchip-exterior.png", 1)));

        //when
        ExtractableResponse<Response> response = callEndpoint(url, params);

        //then
        assertResponseAndExpected(response, List.of(
                findGuideOptionResponse1, findGuideOptionResponse2,
                findGuideOptionResponse3, findGuideOptionResponse4,
                findGuideOptionResponse5, findGuideOptionResponse6));
    }

    private void assertResponseAndExpected(ExtractableResponse<Response> response, List<FindGuideOptionResponse> expectedResponses) {
        SuccessResponse<List<FindGuideOptionResponse>> successResponse = response.body().as(new TypeRef<>() {
        });
        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData()).hasSize(expectedResponses.size());
        softAssertions.assertThat(successResponse.getData())
                .usingRecursiveComparison()
                .isEqualTo(expectedResponses);
    }
}
