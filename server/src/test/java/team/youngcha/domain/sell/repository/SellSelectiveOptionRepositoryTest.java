package team.youngcha.domain.sell.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

@JdbcTest
class SellSelectiveOptionRepositoryTest {

    JdbcTemplate jdbcTemplate;
    SellSelectiveOptionRepository sellSelectiveOptionRepository;

    @Autowired
    public SellSelectiveOptionRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sellSelectiveOptionRepository = new SellSelectiveOptionRepository(new NamedParameterJdbcTemplate(jdbcTemplate));
    }

    @Test
    @DisplayName("트림 견적의 옵션별 판매량을 조회한다.")
    void countByOptionIdForTrim() {
        //given
        jdbcTemplate.update("INSERT INTO car (id, name_ko, name_en) " +
                "VALUES (1, '팰리세이드', 'Palisade');");

        jdbcTemplate.update("INSERT INTO trim (id, name, img_url, background_img_url_web, background_img_url_android, hashtag, price, description, car_id) " +
                "VALUES (1, 'Exclusive', 'https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/exclusivepng.png', " +
                "'trimBackgroundImgUrlWeb', 'trimBackgroundImgUrlAndroid', '기본에 충실', 38960000, '기본에 충실', 1), " +
                "(2, 'Le Blanc (르블랑)', 'https://s3.ap-northeast-2.amazonaws.com/youngcha.team/image/leblanc.png', " +
                "'trimBackgroundImgUrlWeb', 'trimBackgroundImgUrlAndroid', '베스트셀러', 41980000, " +
                "'모두가 선택한 베스트 셀러', 1);");

        jdbcTemplate.update("INSERT INTO category (id, name) " +
                "VALUES (1, '파워 트레인'), " +
                "       (2, '구동 방식'), " +
                "       (3, '바디 타입'), " +
                "       (4, '외장 색상'), " +
                "       (5, '내장 색상'), " +
                "       (6, '휠'), " +
                "       (7, '시스템'), " +
                "       (8, '온도 관리'), " +
                "       (9, '외부 장치'), " +
                "       (10, '내부 장치');");

        jdbcTemplate.update("INSERT INTO options (id, name, price, category_id, feedback_title, feedback_description) " +
                "VALUES (1, '디젤 2.2', 1480000, 1, '디젤 엔진은 효율이 좋아요!', '효율을 중시한다면, 탁월한 선택입니다.'), " +
                "       (2, '가솔린 3.8', 0, 1, '가솔린 엔진은 조용해요!', '조용한 주행을 원하신다면, 탁월한 선택입니다.'), " +
                "       (3, '2WD', 0, 2, '2WD는 효율이 좋아요!', '효율을 중시한다면, 탁월한 선택입니다.'), " +
                "       (4, '4WD', 2370000, 2, '4WD는 파워풀해요!', '힘있는 주행을 원하신다면, 탁월한 선택입니다.'), " +
                "       (5, '7인승', 0, 3, '통로가 있어 쾌적해요!', '쾌적함을 원하신다면, 탁월한 선택입니다.'), " +
                "       (6, '8인승', 0, 3, '많은 사람이 탑승할 수 있어요!', '공간 활용을 원하신다면, 탁월한 선택입니다.'), " +
                "       (7, '어비스 블랙펄', 0, 4, '어비스 블랙펄은 고급스러워요!', '깊은 검정색의 외장색상으로, 차량에 고급스러움과 우아함을 더해줍니다.'), " +
                "       (8, '쉬머링 실버 메탈릭', 0, 4, '쉬머링 실버 메탈릭은 현대적이에요!', '은색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 더해줍니다.'), " +
                "       (9, '문라이트 블루 펄', 0, 4, '문라이트 블루 펄은 활기찬 분위기에요!', '밝은 파란색의 외장색상으로, 차량에 상쾌하고 활기찬 느낌을 줍니다.'), " +
                "       (10, '가이아 브라운 펄', 0, 4, '가이아 브라운 펄은 고급스러워요!', '브라운 계열의 외장색상으로, 차량에 고급스러움과 차분한 분위기를 부여합니다.'), " +
                "       (11, '그라파이트 그레이 메탈릭', 0, 4, '그라파이트 그레이 메탈릭은 세련된 느낌을 줘요!', '회색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 부여합니다.'), " +
                "       (12, '크리미 화이트 펄', 100000, 4, '크리미 화이트 펄은 우아한 분위기에요!', '밝은 화이트(흰색)의 외장색상으로, 차량에 깨끗하고 우아한 느낌을 줍니다.'), " +
                "       (13, '퀄팅천연(블랙)', 0, 5, '블랙 컬러는 클래식한 분위기에요!', '클래식한 분위기를 원하신다면, 탁월한 선택입니다.'), " +
                "       (14, '쿨그레이', 0, 5, '쿨그레이는 환한 분위기에요!', '환한 분위기를 원하신다면, 탁월한 선택입니다.'), " +
                "       (15, '20인치 알로이 휠', 0, 6, '20인치 알로이 휠은 충격흡수가 잘돼요!', '뛰어난 승차감을 원하신다면, 탁월한 선택입니다.'), " +
                "       (16, '20인치 다크 스퍼터링 휠', 840000, 6, '20인치 다크 스퍼터링 휠은 깔끔한 디자인이에요!', '세련되고 깔끔한 디자인을 원하신다면, 탁월한 선택입니다.'), " +
                "       (17, '20인치 블랙톤 전면 가공 휠', 840000, 6, '20인치 블랙톤 전면 가공 휠은 무게감이 있어요!', '무게감있는 디자인을 원하신다면, 탁월한 선택입니다.'), " +
                "       (18, '알콘(alcon) 단조 브레이크 & 20인치 휠 패키지', 3660000, 6, '알콘(alcon) 단조 브레이크 & 20인치 휠 패키지는 뛰어난 제동력이 강점이에요!', '안전과 디자인을 모두 원하신다면, 탁월한 선택입니다.'), " +
                "       (19, '현대스마트센스 1', 790000, 7, '', null), " +
                "       (20, '주차보조 시스템 2', 690000, 7, '', null), " +
                "       (21, '컴포트 2', 1090000, 7, '', null), " +
                "       (22, '2열 통풍시트', 400000, 8, '', null), " +
                "       (23, '적외선 무릎워머', 300000, 8, '', null), " +
                "       (24, '차량 보호 필름', 490000, 9, '', null), " +
                "       (25, '사이드 스텝', 350000, 9, '', null), " +
                "       (26, '듀얼 머플러 패키지', 840000, 9, '', null), " +
                "       (27, '듀얼 와이드 선루프', 890000, 10, '', null), " +
                "       (28, '프로텍션 매트 패키지 1', 250000, 10, '', null), " +
                "       (29, '빌트인 공기청정기', 400000, 10, '', null), " +
                "       (30, '빌트인 캠(보조배터리 포함)', 690000, 10, '', null);");

        jdbcTemplate.update("INSERT INTO sell " +
                "VALUES (1, 2, 1, 5, 3, 12, 14, 15, 38, 0, '2023-05-29 00:53:43'), " +
                "       (2, 2, 1, 5, 3, 9, 14, 15, 35, 1, '2022-12-02 00:53:43'), " +
                "       (3, 2, 1, 5, 3, 12, 13, 15, 46, 1, '2023-02-03 00:53:43');");

        jdbcTemplate.update("INSERT INTO sell_selective_options " +
                "VALUES (5, 1, 19), " +
                "       (1, 1, 27), " +
                "       (4, 1, 27), " +
                "       (3, 1, 28), " +
                "       (7, 2, 19), " +
                "       (6, 2, 23), " +
                "       (11, 3, 23), " +
                "       (12, 3, 23), " +
                "       (15, 3, 23), " +
                "       (14, 3, 28);");

        Map<Long, Long> expected = new HashMap<>();
        expected.put(19L, 2L);
        expected.put(23L, 4L);
        expected.put(27L, 2L);
        expected.put(28L, 2L);

        //when
        Map<Long, Long> actual = sellSelectiveOptionRepository.countByOptionIdForTrim(2L);

        //then
        Assertions.assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

}