package team.youngcha.domain.sell.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import team.youngcha.domain.category.enums.RequiredCategory;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SellRepositoryTest {

    JdbcTemplate jdbcTemplate;
    SellRepository sellRepository;

    @Autowired
    SellRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sellRepository = new SellRepository(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()));
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into car (id, name_ko, name_en) values (1,'자동차', 'car')");
        jdbcTemplate.update("insert into trim (id, name, img_url, background_img_url_web, background_img_url_android, hashtag, price, description, car_id)" +
                " values (1, 'Le Blanc', 'Le_Blanc_img.jpg', 'Le_Blanc_back_web', 'Le_Blanc_back_android', '#베스트셀러', 10000000, '베스트셀러', 1)");
        jdbcTemplate.update("insert into trim (id, name, img_url, background_img_url_web, background_img_url_android, hashtag, price, description, car_id)" +
                " values (2, 'Exclusive', 'Exclusive_img.jpg', 'Exclusive_back_web', 'Exclusive_back_android', '#기본', 9000000, '기본', 1)");
        jdbcTemplate.update("insert into category (id, name) values (1, '파워 트레인')");
        jdbcTemplate.update("insert into options (id, name, price, feedback_title, feedback_description, category_id) " +
                "values (1,'a', 0, 'b', 'c', 1)");
        jdbcTemplate.update("insert into options (id, name, price, feedback_title, feedback_description, category_id) " +
                "values (2,'b', 0, 'b', 'c', 1)");
        jdbcTemplate.update("insert into options (id, name, price, feedback_title, feedback_description, category_id) " +
                "values (3,'c', 0, 'b', 'c', 1)");
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (1, 1, 1, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (2, 1, 1, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (3, 1, 2, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");
        jdbcTemplate.update("insert into sell (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, age, gender, create_date) " +
                "values (4, 2, 1, 1, 1, 1, 1, 1, 50, 0, '2023-01-01 12:12:12')");
    }

    @Test
    @DisplayName("파워 트레인 options 별로 판매수를 조회한다.")
    void countSellPowerTrain() {
        //given
        Long trimId = 1L;
        List<Long> optionIds = List.of(1L, 2L);

        //when
        Map<Long, Long> counts = sellRepository.
                countOptionsByTrimIdAndContainOptionsIds(trimId, optionIds, RequiredCategory.POWER_TRAIN);

        //then
        assertThat(counts.size()).isEqualTo(2);
        assertThat(counts.get(1L)).isEqualTo(2);
        assertThat(counts.get(2L)).isEqualTo(1);
    }

}