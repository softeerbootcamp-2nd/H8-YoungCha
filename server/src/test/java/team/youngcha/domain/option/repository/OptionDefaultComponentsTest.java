package team.youngcha.domain.option.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.domain.option.dto.DefaultOptionSummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JdbcTest
@Sql({"classpath:data/car.sql", "classpath:data/category.sql", "classpath:data/trim.sql", "classpath:data/options.sql", "classpath:data/trim_options.sql", "classpath:data/options_image.sql"})
public class OptionDefaultComponentsTest {

    JdbcTemplate jdbcTemplate;
    OptionRepository optionRepository;

    Long trimId = 2L;
    Long categoryId = 1L;
    int pageSize = 5;
    int totalPages = 2;

    List<DefaultOptionSummary> expectedDefaultOptionSummaries = new ArrayList<>(Arrays.asList(
            new DefaultOptionSummary("8단 자동변속기", categoryId, "https://www.hyundai.com/contents/spec/LX24/8at_s.jpg"),
            new DefaultOptionSummary("ISG 시스템", categoryId, "https://www.hyundai.com/contents/spec/LX24/isg_s.jpg"),
            new DefaultOptionSummary("통합주행모드", categoryId, "https://www.hyundai.com/contents/spec/LX24/drivemode_s.jpg"),
            new DefaultOptionSummary("랙구동형 전동식 파워스티어링(R-MDPS)", categoryId, "https://www.hyundai.com/contents/spec/LX24/rmdps_s.jpg"),
            new DefaultOptionSummary("전자식 변속버튼", categoryId, "https://www.hyundai.com/contents/spec/LX24/sbw_s.jpg"),
            new DefaultOptionSummary("HTARC", categoryId, "https://www.hyundai.com/contents/spec/LX24/htrac_s.jpg"),
            new DefaultOptionSummary("험로주행모드", categoryId, "https://www.hyundai.com/contents/spec/LX24/tractionmode_s.jpg")));

    @Autowired
    public OptionDefaultComponentsTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.optionRepository = new OptionRepository(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()));
    }

    @Test
    @DisplayName("첫번째 페이지의 조회를 검증한다")
    void findFirstPage() {
        //given
        int targetPage = 1;

        //when
        List<DefaultOptionSummary> actualDefaultOptionSummaries =
                optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(trimId, categoryId, targetPage, pageSize);

        //then
        Assertions.assertThat(actualDefaultOptionSummaries)
                .usingRecursiveComparison()
                .isEqualTo(expectedDefaultOptionSummaries.subList(0, 5));
    }

    @Test
    @DisplayName("마지막 페이지의 조회를 검증한다")
    void findLastPage() {
        //given
        int targetPage = totalPages;

        //when
        List<DefaultOptionSummary> actualDefaultOptionSummaries =
                optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(trimId, categoryId, targetPage, pageSize);

        //then
        Assertions.assertThat(actualDefaultOptionSummaries)
                .usingRecursiveComparison()
                .isEqualTo(expectedDefaultOptionSummaries.subList(5, 7));
    }

}
