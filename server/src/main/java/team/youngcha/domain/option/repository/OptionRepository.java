package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.dto.DefaultOptionSummary;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionType;
import team.youngcha.domain.trim.enums.TrimOptionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Option> optionRowMapper = new OptionRowMapper();
    private final RowMapper<DefaultOptionSummary> defaultOptionSummaryRowMapper = new DefaultOptionSummaryRowMapper();

    public List<Option> findOptionsByTrimIdAndType(Long trimId, OptionType type, SelectiveCategory name) {

        return jdbcTemplate.query("select * from options " +
                        "join category on options.category_id = category.id and category.name = ? " +
                        "join trim_options on options.id = trim_options.options_id " +
                        "and trim_options.trim_id = ? and trim_options.type = ? ",
                optionRowMapper, name.getName(), trimId, type.getType());
    }

    private static class OptionRowMapper implements RowMapper<Option> {
        @Override
        public Option mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Option.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .feedback(resultSet.getString("feedback"))
                    .categoryId(resultSet.getLong("category_id"))
                    .build();
        }
    }

    public int countDefaultOptionsByTrimIdAndCategoryId(Long trimId, Long categoryId) {
        String sql = "SELECT COUNT(*) FROM trim " +
                "JOIN trim_options ON trim.id = trim_options.trim_id and trim_options.type = ? " +
                "JOIN options ON trim_options.options_id = options.id and options.category_id = ? " +
                "WHERE trim.id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                OptionType.BASIC.getType(),
                categoryId,
                trimId);

        if (count == null) {
            return 0;
        }
        return count;
    }

    public List<DefaultOptionSummary> findPaginatedDefaultOptionsByTrimIdAndCategoryId(Long trimId, Long categoryId, int page, int size) {
        int offset = (page - 1) * size;

        String sql = "SELECT " +
                "options.id AS optionsId, " +
                "options.name AS optionsName, " +
                "options.category_id AS optionsCategoryId, " +
                "options_image.img_url AS optionsImgUrl " +
                "FROM trim " +
                "JOIN trim_options ON trim.id = trim_options.trim_id AND trim_options.type = ? " +
                "JOIN options ON trim_options.options_id = options.id AND options.category_id = ? " +
                "LEFT JOIN options_image ON options.id = options_image.options_id " +
                "WHERE trim.id = ? " +
                "ORDER BY options.id LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, defaultOptionSummaryRowMapper,
                TrimOptionType.DEFAULT.getValue(),
                categoryId,
                trimId,
                size,
                offset);
    }

    private static class DefaultOptionSummaryRowMapper implements RowMapper<DefaultOptionSummary> {
        @Override
        public DefaultOptionSummary mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new DefaultOptionSummary(
                    resultSet.getString("optionsName"),
                    resultSet.getLong("optionsCategoryId"),
                    resultSet.getString("optionsImgUrl"));
        }
    }
}
