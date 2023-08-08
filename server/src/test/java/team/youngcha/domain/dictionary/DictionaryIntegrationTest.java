package team.youngcha.domain.dictionary;

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
import team.youngcha.domain.dictionary.dto.FindDictionaryResponse;

import java.util.List;

@Sql({"classpath:data/dictionary.sql"})
public class DictionaryIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("백과사전의 전체 항목을 조회한다")
    void findDictionary() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()

                .when()
                .get("/car-make/dictionary")

                .then()
                .log().all()
                .extract();

        SuccessResponse<List<FindDictionaryResponse>> successResponse = response.body().as(new TypeRef<>() {
        });

        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        softAssertions.assertThat(successResponse.getData().size()).isEqualTo(2);

        softAssertions.assertThat(successResponse.getData().get(0).getWord()).isEqualTo("머플러");
        softAssertions.assertThat(successResponse.getData().get(0).getDescription()).isEqualTo("엔진에서 발생한 배기가스를 차 밖으로 배출하는 장치에요.");
        softAssertions.assertThat(successResponse.getData().get(0).getImgUrl()).isEqualTo("http://kids.hyundai.com/upload/image/FU/201704/170425_car_img06.png");

        softAssertions.assertThat(successResponse.getData().get(1).getWord()).isEqualTo("파워 트레인");
        softAssertions.assertThat(successResponse.getData().get(1).getDescription()).isEqualTo("엔진에서 구동바퀴사이의 모든 기관을 지칭하는 말이에요. 자동차 플랫폼과 비슷한 뜻이에요.");
        softAssertions.assertThat(successResponse.getData().get(1).getImgUrl()).isEqualTo(null);

        softAssertions.assertAll();
    }

}
