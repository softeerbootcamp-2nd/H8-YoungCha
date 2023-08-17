package team.youngcha.domain.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Category> categoryRowMapper = new CategoryRowMapper();

    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM category", categoryRowMapper);
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Category(rs.getLong("id"), rs.getString("name"));
        }
    }
}
