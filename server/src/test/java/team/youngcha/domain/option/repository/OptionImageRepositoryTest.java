package team.youngcha.domain.option.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import team.youngcha.domain.option.entity.OptionImage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class OptionImageRepositoryTest {

    JdbcTemplate jdbcTemplate;
    OptionImageRepository optionImageRepository;

    @Autowired
    OptionImageRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.optionImageRepository = new OptionImageRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into category (id, name) values (1, '파워 트레인')");
        jdbcTemplate.update("insert into category (id, name) values (2, '바디 타입')");
        jdbcTemplate.update("insert into options (id, name, price, feedback, category_id) " +
                "values (1,'디젤', 1000, '좋아요', 1)");
        jdbcTemplate.update("insert into options (id, name, price, feedback, category_id) " +
                "values (2,'가솔린', 2000, '추천', 1)");
        jdbcTemplate.update("insert into options (id, name, price, feedback, category_id) " +
                "values (3,'mock1', 0, 'mock1', 1)");
        jdbcTemplate.update("insert into options (id, name, price, feedback, category_id) " +
                "values (4,'mock2', 0, 'mock2', 2)");
        jdbcTemplate.update("insert into options_image (id, img_url, img_type, options_id) " +
                "values (1, '디젤 img1', 0, 1)");
        jdbcTemplate.update("insert into options_image (id, img_url, img_type, options_id) " +
                "values (2, '디젤 img2', 1, 1)");
        jdbcTemplate.update("insert into options_image (id, img_url, img_type, options_id) " +
                "values (3, '가솔린 img1', 0, 2)");
        jdbcTemplate.update("insert into options_image (id, img_url, img_type, options_id) " +
                "values (4, 'mock', 0, 3)");
    }

    @Test
    @DisplayName("option id가 포함된 option image를 조회한다.")
    void findOptionImageContainOptionIds() {
        //given
        List<Long> optionsIds = List.of(1L, 2L);

        //when
        List<OptionImage> optionImages = optionImageRepository.findByContainOptionIds(optionsIds);

        //then
        OptionImage optionImage1 = OptionImage.builder()
                .id(1L).imgUrl("디젤 img1")
                .imgType(0).optionId(1L)
                .build();

        OptionImage optionImage2 = OptionImage.builder()
                .id(2L).imgUrl("디젤 img2")
                .imgType(1).optionId(1L)
                .build();

        OptionImage optionImage3 = OptionImage.builder()
                .id(3L).imgUrl("가솔린 img1")
                .imgType(0).optionId(2L)
                .build();

        assertThat(optionImages).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(optionImage1, optionImage2, optionImage3));
    }
}