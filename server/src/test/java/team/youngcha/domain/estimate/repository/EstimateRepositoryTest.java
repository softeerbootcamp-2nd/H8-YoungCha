package team.youngcha.domain.estimate.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.option.dto.GuideInfo;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:data/car.sql",
        "classpath:data/trim.sql",
        "classpath:data/category.sql",
        "classpath:data/options.sql",
        "classpath:data/keyword.sql"
})
class EstimateRepositoryTest {

    JdbcTemplate jdbcTemplate;

    EstimateRepository estimateRepository;

    @Autowired
    EstimateRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.estimateRepository = new EstimateRepository(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()));
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into estimate (id, trim_id, engine_id, body_type_id, driving_system_id, exterior_color_id, interior_color_id, wheel_id, keyword1_id, keyword2_id, keyword3_id, age_range, gender, create_date) " +
                "values (1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12'), " +
                "(2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(3, 2, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(4, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(5, 2, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(6, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(7, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(8, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(9, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(10, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 3, 0, '2023-01-01 12:12:12')," +
                "(11, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 4, 0, '2023-01-01 12:12:12')," +
                "(12, 2, 3, 1, 1, 1, 1, 1, 1, 2, 3, 2, 0, '2023-01-01 12:12:12')," +
                "(13, 2, 2, 1, 1, 1, 1, 1, 1, 2, 4, 2, 0, '2023-01-01 12:12:12')," +
                "(14, 2, 1, 1, 1, 1, 1, 1, 1, 2, 4, 2, 0, '2023-01-01 12:12:12')," +
                "(15, 2, 1, 1, 1, 1, 1, 1, 1, 4, 5, 2, 0, '2023-01-01 12:12:12')");
    }

    @Test
    @DisplayName("유사 사용자의 수를 계산한다.")
    void countPowerTrainsSimilarityUsers() {
        //given
        Long trimId = 2L;
        List<Long> powerTrainIds = List.of(1L, 2L);
        GuideInfo guideInfo = GuideInfo.builder()
                .keywordIds(List.of(1L, 2L, 3L))
                .gender(Gender.MALE)
                .ageRange(AgeRange.AGE_20).build();

        //when
        Map<Long, Long> similarityUsers = estimateRepository.countPowerTrainsSimilarityUsers(trimId, powerTrainIds, guideInfo);

        //then
        assertThat(similarityUsers).hasSize(2);
        assertThat(similarityUsers.get(1L)).isEqualTo(4);
        assertThat(similarityUsers.get(2L)).isEqualTo(5);
    }

    @Test
    @DisplayName("트림 내에서, '키워드+옵션아이디가 겹치는 수/키워드만 겹치는 수'(비율)를 계산한다.")
    void calculateRate() {
        //given
        Long trimId = 2L;
        Long optionId = 1L;
        Long keywordId = 4L;

        //when
        Integer rate = estimateRepository.calculateRate(trimId, optionId, keywordId);

        //then
        assertThat(rate).isEqualTo(Math.round((float) 2 / 6 * 100));
    }
}