package team.youngcha.domain.car;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.IntegrationTestBase;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.car.dto.CarResponse;
import team.youngcha.domain.car.dto.FindCarsResponse;
import team.youngcha.domain.car.entity.Car;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Sql("classpath:data/car.sql")
public class FindCarsIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("자동차 모델 목록 조회 API 호출 응답을 검증한다")
    void findCars() {
        //given
        String url = "/cars";

        List<CarResponse> carResponses = Stream.of(new Car(1L, "팰리세이드"), new Car(2L, "넥쏘"), new Car(3L, "디올 뉴 싼타페"))
                .map(CarResponse::new)
                .collect(Collectors.toList());

        FindCarsResponse expectedResponseBodyData = new FindCarsResponse(carResponses);

        //when
        ExtractableResponse<Response> response = callEndpoint(url, null);

        SuccessResponse<FindCarsResponse> responseBody = response.body().as(new TypeRef<>() {
        });

        //then
        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        softAssertions.assertThat(responseBody.getData())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedResponseBodyData);
    }
}
