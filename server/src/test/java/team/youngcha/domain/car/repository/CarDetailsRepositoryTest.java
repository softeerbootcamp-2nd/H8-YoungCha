package team.youngcha.domain.car.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.domain.car.dto.CarDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Sql({"classpath:data/car_details.sql"})
@JdbcTest
public class CarDetailsRepositoryTest {

    CarDetailsRepository carDetailsRepository;

    @Autowired
    CarDetailsRepositoryTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.carDetailsRepository = new CarDetailsRepository(namedParameterJdbcTemplate);
    }

    @Test
    @DisplayName("자동차의 상세 정보를 조회한다")
    void findDetails() {
        //given
        List<CarDetails> carDetails = new ArrayList<>(Arrays.asList(
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "베젤리스 인사이드 미러", "내관", 3, "inside-mirror.jpg"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "내비게이션 기반 스마트 크루즈 컨트롤 (안전구간, 곡선로)", "지능형 안전기술", 3, "cruise-control.jpg"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "12.3인치 내비게이션(블루링크, 폰 프로젝션, 현대 카페이)", "멀티미디어", 3, "12-3-navigation.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "어비스 블랙펄", "외장 색상", 1, "black-pearl.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "쉬머링 실버 메탈릭", "외장 색상", 1, "silver-metalic.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "문라이트 블루 펄", "외장 색상", 1, "blue-pearl.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "가이아 브라운 펄", "외장 색상", 1, "brown-pearl.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "그라파이트 그레이 메탈릭", "외장 색상", 1, "gray-metalic.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "크리미 화이트 펄", "외장 색상", 1, "white-pearl.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "퀄팅천연(블랙)", "내장 색상", 1, "qualting-black.png"),
                new CarDetails(2L, "Le Blanc (르블랑)", "leblancImgUrl", "leblancBgImgUrl", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "쿨그레이", "내장 색상", 1, "cool-gray"),
                new CarDetails(5L, "Guide Mode", "guideModeImgUrl", "guideModeBgImgUrl", "나만을 위한 팰리세이드", 38960000, null, null, null, null, null, null)
        ));

        //when
        List<CarDetails> findCarDetails = carDetailsRepository.findDetails(1L);

        //then
        Assertions.assertThat(findCarDetails)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(carDetails);
    }
}
