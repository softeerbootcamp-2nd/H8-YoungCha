package team.youngcha.domain.option.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.enums.OptionType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class OptionRepositoryTest {

    JdbcTemplate jdbcTemplate;
    OptionRepository optionRepository;

    @Autowired
    public OptionRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.optionRepository = new OptionRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into car (id, name) values (1,'car')");
        jdbcTemplate.update("insert into trim (id, name, img_url, background_img_url, hashtag, price, description, car_id)" +
                " values (1, 'Le Blanc', 'Le_Blanc_img.jpg', 'Le_Blanc_back', '#베스트셀러', 10000000, '베스트셀러', 1)," +
                "(2, 'Exclusive', 'Exclusive_img.jpg', 'Exclusive_back', '#기본', 9000000, '기본', 1)");
        jdbcTemplate.update("insert into category (id, name) values (1, '파워 트레인')");
        jdbcTemplate.update("insert into options (id, name, price, feedback, category_id) " +
                "values (1,'디젤', 1000, '좋아요', 1)," +
                "(2,'가솔린', 2000, '추천', 1)," +
                "(3,'mock', 0, 'mock', 1)");
        jdbcTemplate.update("insert into trim_options (id, type, trim_id, options_id) " +
                "values (1, " + OptionType.OPTIONAL.getType() + ", 1, 1)," +
                "(2," + OptionType.OPTIONAL.getType() + ", 1, 2)," +
                "(3, " + OptionType.BASIC.getType() + ", 1, 2)," +
                "(4, " + OptionType.OPTIONAL.getType() + ", 2, 2)");
    }

    @Test
    @DisplayName("trim id와 부품 타입으로 파워 트레인을 조회한다.")
    void findPowerTrain() {
        //given
        Long trimId = 1L;

        //when
        List<Option> powerTrains = optionRepository.
                findOptionsByTrimIdAndType(trimId, OptionType.OPTIONAL, SelectiveCategory.POWER_TRAIN);

        //then
        Option diesel = Option.builder()
                .id(1L).name("디젤").price(1000).feedback("좋아요").categoryId(1L)
                .build();
        Option gasoline = Option.builder()
                .id(2L).name("가솔린").price(2000).feedback("추천").categoryId(1L)
                .build();
        assertThat(powerTrains)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(diesel, gasoline));
    }
}