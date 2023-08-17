package team.youngcha.domain.category.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import team.youngcha.domain.category.entity.Category;

import java.util.List;

@JdbcTest
class CategoryRepositoryTest {

    JdbcTemplate jdbcTemplate;

    CategoryRepository categoryRepository;

    @Autowired
    CategoryRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRepository = new CategoryRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("옵션의 카테고리 목록을 조회한다.")
    void findAll() {
        //given
        jdbcTemplate.execute("INSERT INTO category VALUES " +
                "(1, '파워 트레인'), " +
                "(2, '구동 방식'), " +
                "(3, '바디 타입')");

        List<Category> expected = List.of(
                new Category(1L, "파워 트레인"),
                new Category(2L, "구동 방식"),
                new Category(3L, "바디 타입"));

        //when
        List<Category> actual = categoryRepository.findAll();

        //then
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

}
