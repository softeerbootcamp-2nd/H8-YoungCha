package team.youngcha.domain.keyword.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import team.youngcha.domain.keyword.entity.Keyword;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:data/keyword.sql",
        "classpath:data/category.sql"})
class KeywordRepositoryTest {

    JdbcTemplate jdbcTemplate;
    KeywordRepository keywordRepository;

    @Autowired
    KeywordRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.keywordRepository = new KeywordRepository(new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()));
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into options (id, name, price, feedback_title, feedback_description, category_id) " +
                "values (1, 'a', 0, 'f', 'f', 1)," +
                "(2, 'b', 0, 'f', 'f', 1)," +
                "(3, 'c', 0, 'f', 'f', 1)," +
                "(4, 'd', 0, 'f', 'f', 1)," +
                "(5, 'e', 0, 'f', 'f', 1)");
        jdbcTemplate.update("insert into options_keyword (id, options_id, keyword_id) " +
                "values (1, 1, 1)," +
                "(2, 2, 1)," +
                "(3, 2, 2)," +
                "(4, 2, 3)," +
                "(5, 3, 2)," +
                "(6, 3, 4)," +
                "(7, 4, 2);");
    }

    @Test
    void findByContainOptionIdsGroupByOptionId() {
        //given
        List<Long> optionsIds = List.of(2L, 3L);

        //when
        Map<Long, List<Keyword>> groupKeywords = keywordRepository.
                findByContainOptionIdsAndGroupKeywords(optionsIds);

        //then
        Keyword 주행력 = Keyword.builder()
                .id(1L).name("주행력")
                .build();
        Keyword 소음 = Keyword.builder()
                .id(2L).name("소음")
                .build();
        Keyword 효율 = Keyword.builder()
                .id(3L).name("효율")
                .build();
        Keyword 파워 = Keyword.builder()
                .id(4L).name("파워")
                .build();
        assertThat(groupKeywords).hasSize(2);
        assertThat(groupKeywords.get(2L)).usingRecursiveComparison()
                .isEqualTo(List.of(주행력, 소음, 효율));
        assertThat(groupKeywords.get(3L)).usingRecursiveComparison()
                .isEqualTo(List.of(소음, 파워));
    }
}