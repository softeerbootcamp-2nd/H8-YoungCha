package team.youngcha.domain.car;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.car.dto.FindCarDetailsResponse;
import team.youngcha.domain.car.dto.GuideModeDetails;
import team.youngcha.domain.car.dto.ModelName;
import team.youngcha.domain.car.dto.TrimBackgroundImgUrl;
import team.youngcha.domain.option.dto.OptionSummary;
import team.youngcha.domain.trim.dto.TrimDetail;

import java.util.List;

@Sql({"classpath:data/car_details.sql"})
public class FindCarDetailsIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("자동차의 상세정보를 조회한다")
    void findCarDetails() {
        //given
        ModelName modelName = new ModelName("팰리세이드", "Palisade");

        GuideModeDetails guideModeDetails = new GuideModeDetails(new TrimBackgroundImgUrl("guideModeBgImgUrlWeb", "guideModeBgImgUrlAndroid"),
                "나만을 위한 팰리세이드", 38960000);

        TrimDetail leblancTrimDetail = new TrimDetail(
                2L,
                "Le Blanc (르블랑)",
                new TrimBackgroundImgUrl("leblancBgImgUrlWeb", "leblancBgImgUrlAndroid"),
                "leblancImgUrl",
                "베스트셀러",
                "모두가 선택한 베스트셀러",
                true,
                40440000);


        leblancTrimDetail.getMainOptions().add(new OptionSummary("내비게이션 기반 스마트 크루즈 컨트롤 (안전구간, 곡선로)", "cruise-control.jpg"));
        leblancTrimDetail.getMainOptions().add(new OptionSummary("베젤리스 인사이드 미러", "inside-mirror.jpg"));
        leblancTrimDetail.getMainOptions().add(new OptionSummary("12.3인치 내비게이션(블루링크, 폰 프로젝션, 현대 카페이)", "12-3-navigation.png"));

        leblancTrimDetail.getExteriorColors().add(new OptionSummary("어비스 블랙펄", "black-pearl.png"));
        leblancTrimDetail.getExteriorColors().add(new OptionSummary("쉬머링 실버 메탈릭", "silver-metalic.png"));
        leblancTrimDetail.getExteriorColors().add(new OptionSummary("문라이트 블루 펄", "blue-pearl.png"));
        leblancTrimDetail.getExteriorColors().add(new OptionSummary("가이아 브라운 펄", "brown-pearl.png"));
        leblancTrimDetail.getExteriorColors().add(new OptionSummary("그라파이트 그레이 메탈릭", "gray-metalic.png"));
        leblancTrimDetail.getExteriorColors().add(new OptionSummary("크리미 화이트 펄", "white-pearl.png"));

        leblancTrimDetail.getInteriorColors().add(new OptionSummary("퀄팅천연(블랙)", "qualting-black.png"));
        leblancTrimDetail.getInteriorColors().add(new OptionSummary("쿨그레이", "cool-gray"));

        FindCarDetailsResponse expectedResponse = new FindCarDetailsResponse(modelName, guideModeDetails, List.of(leblancTrimDetail));

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()

                .when()
                .get("/cars/1/details")

                .then().log().all()
                .extract();

        //then
        SuccessResponse<FindCarDetailsResponse> successResponse = response.body().as(new TypeRef<>() {
        });

        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData())
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }
}
