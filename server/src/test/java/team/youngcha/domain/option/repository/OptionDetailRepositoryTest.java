package team.youngcha.domain.option.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import team.youngcha.domain.option.entity.OptionDetail;
import team.youngcha.domain.option.entity.Spec;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class OptionDetailRepositoryTest {

    JdbcTemplate jdbcTemplate;
    OptionDetailRepository optionDetailRepository;

    @Autowired
    OptionDetailRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.optionDetailRepository = new OptionDetailRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into category (id, name) " +
                "values (1, '파워 트레인'), " +
                "(2, '바디 타입')");
        jdbcTemplate.update("insert into options (id, name, price, feedback, category_id) " +
                "values (1,'디젤', 1000, '좋아요', 1), " +
                "(2,'가솔린', 2000, '추천', 1), " +
                "(3,'mock1', 0, 'mock1', 1), " +
                "(4,'mock2', 0, 'mock2', 2)");
        jdbcTemplate.update("insert into options_detail (id, name, description, img_url, options_id) " +
                "values (1, '디젤1', '디젤 설명1', '디젤 img1', 1), " +
                "(2, '디젤2', '디젤 설명2', '디젤 img2', 1), " +
                "(3, '가솔린1', '가솔린 설명1', '가솔린 img1', 2), " +
                "(4, 'mock', 'mock', 'mock', 3)");
        jdbcTemplate.update("insert into spec (id, name, description, options_detail_id) " +
                "values (1, '최고출력', '3800 rpm', 2)," +
                "(2, '최대토크', '2750 rpm', 2)");
    }

    @Test
    @DisplayName("option id가 포함된 option detail을 조회한다.")
    void findContainOptionIds() {
        //given
        List<Long> optionsIds = List.of(1L, 2L);

        //when
        List<OptionDetail> optionDetails = optionDetailRepository.findWithSpecsByContainOptionIds(optionsIds);

        //then
        OptionDetail optionDetail1 = OptionDetail.builder()
                .id(1L).name("디젤1")
                .description("디젤 설명1")
                .imgUrl("디젤 img1")
                .optionId(1L)
                .specs(new ArrayList<>())
                .build();

        Spec spec1 = Spec.builder()
                .id(1L)
                .name("최고출력")
                .description("3800 rpm")
                .optionDetailId(2L)
                .build();
        Spec spec2 = Spec.builder()
                .id(2L)
                .name("최대토크")
                .description("2750 rpm")
                .optionDetailId(2L)
                .build();

        OptionDetail optionDetail2 = OptionDetail.builder()
                .id(2L).name("디젤2")
                .description("디젤 설명2")
                .imgUrl("디젤 img2")
                .optionId(1L)
                .specs(List.of(spec1, spec2))
                .build();

        OptionDetail optionDetail3 = OptionDetail.builder()
                .id(3L).name("가솔린1")
                .description("가솔린 설명1")
                .imgUrl("가솔린 img1")
                .optionId(2L)
                .specs(new ArrayList<>())
                .build();

        assertThat(optionDetails.size()).isEqualTo(3);
        assertThat(optionDetails).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(optionDetail1, optionDetail2, optionDetail3));
    }

}