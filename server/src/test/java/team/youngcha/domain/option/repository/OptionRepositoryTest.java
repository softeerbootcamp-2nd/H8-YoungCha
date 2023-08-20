package team.youngcha.domain.option.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import team.youngcha.domain.category.enums.RequiredCategory;
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
        this.optionRepository = new OptionRepository(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()));
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into car (id, name) values (1,'car')");
        jdbcTemplate.update("insert into trim (id, name, img_url, background_img_url, hashtag, price, description, car_id)" +
                " values (1, 'Le Blanc', 'Le_Blanc_img.jpg', 'Le_Blanc_back', '#베스트셀러', 10000000, '베스트셀러', 1)," +
                "(2, 'Exclusive', 'Exclusive_img.jpg', 'Exclusive_back', '#기본', 9000000, '기본', 1)");

    }

    @Test
    @DisplayName("trim id와 부품 타입으로 파워 트레인을 조회한다.")
    void findPowerTrain() {
        //given
        jdbcTemplate.update("insert into category (id, name) values (1, '파워 트레인')");
        jdbcTemplate.update("insert into options (id, name, price, feedback_title, feedback_description, category_id) " +
                "values (1,'디젤', 1000, '좋아요', '좋습니다', 1)," +
                "(2,'가솔린', 2000, '추천', '추천합니다', 1)," +
                "(3,'mock', 0, 'mock', 'is mock', 1)");
        jdbcTemplate.update("insert into trim_options (id, type, trim_id, options_id) " +
                "values (1, " + OptionType.REQUIRED.getType() + ", 1, 1)," +
                "(2," + OptionType.REQUIRED.getType() + ", 1, 2)," +
                "(3, " + OptionType.BASIC.getType() + ", 1, 2)," +
                "(4, " + OptionType.REQUIRED.getType() + ", 2, 2)");

        Long trimId = 1L;

        //when
        List<Option> powerTrains = optionRepository.
                findRequiredOptionsByTrimIdAndOptionType(trimId, OptionType.REQUIRED, RequiredCategory.POWER_TRAIN);

        //then
        Option diesel = Option.builder()
                .id(1L).name("디젤").price(1000)
                .feedbackTitle("좋아요").feedbackDescription("좋습니다")
                .categoryId(1L)
                .build();
        Option gasoline = Option.builder()
                .id(2L).name("가솔린").price(2000)
                .feedbackTitle("추천").feedbackDescription("추천합니다")
                .categoryId(1L)
                .build();
        assertThat(powerTrains)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(diesel, gasoline));
    }

    @Nested
    class FindInteriorColor {

        @BeforeEach
        void setUp() {
            jdbcTemplate.update("insert into category (id, name) " +
                    "values (1, '" + RequiredCategory.EXTERIOR_COLOR.getName() + "')," +
                    "(2, '" + RequiredCategory.INTERIOR_COLOR.getName() + "')");
            jdbcTemplate.update("insert into options (id, name, price, feedback_title, feedback_description, category_id) " +
                    "values (1,'blue', 0, 'blue feedback', 'blue description', 1)," + // 외장 색상
                    "(2,'black', 0, 'black feedback', 'black description', 1)," +
                    "(3,'white', 0, 'white feedback', 'white description', 1)," +

                    "(4, 'in1', 0, 'in1 feedback', 'in1 description', 2)," + // 내장 색상
                    "(5, 'in2', 0, 'in2 feedback', 'in2 description', 2)," +
                    "(6, 'in3', 1000, 'in3 feedback', 'in3 description', 2)");
            jdbcTemplate.update("insert into trim_options (id, type, trim_id, options_id) " +
                    "values (1, " + OptionType.REQUIRED.getType() + ", 1, 4)," +
                    "(2," + OptionType.REQUIRED.getType() + ", 1, 5)," +
                    "(3, " + OptionType.REQUIRED.getType() + ", 1, 6)");
            jdbcTemplate.update("insert into options_relation (id, parent_id, child_id) " +
                    "values (1, 1, 4)," +
                    "(2, 1, 6)," +
                    "(3, 2, 5)");
        }

        Option inColor1 = Option.builder()
                .id(4L).price(0)
                .name("in1")
                .feedbackTitle("in1 feedback")
                .feedbackDescription("in1 description")
                .categoryId(2L).build();
        Option inColor2 = Option.builder()
                .id(5L).price(0)
                .name("in2")
                .feedbackTitle("in2 feedback")
                .feedbackDescription("in2 description")
                .categoryId(2L).build();
        Option inColor3 = Option.builder()
                .id(6L).price(1000)
                .name("in3")
                .feedbackTitle("in3 feedback")
                .feedbackDescription("in3 description")
                .categoryId(2L).build();

        @Test
        @DisplayName("외장 색상과 연관 관계가 있는 내장 색상을 조회한다.")
        void findInteriorColorAssociateWithExteriorColor() {
            //given
            Long trimId = 1L;
            Long exteriorColorId = 1L;

            //when
            List<Option> interiorColors = optionRepository
                    .findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId);

            //then
            assertThat(interiorColors).usingRecursiveComparison()
                    .isEqualTo(List.of(inColor1, inColor3));
        }

        @Test
        @DisplayName("외장 색상과 연관 관계가 있는 내장 색상이 없으면 관련 색상 모두를 조회한다.")
        void findInteriorColorAll() {
            //given
            Long trimId = 1L;
            Long exteriorColorId = 3L;

            //when
            List<Option> interiorColors = optionRepository
                    .findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId);

            //then
            assertThat(interiorColors).usingRecursiveComparison()
                    .isEqualTo(List.of(inColor1, inColor2, inColor3));
        }
    }
}