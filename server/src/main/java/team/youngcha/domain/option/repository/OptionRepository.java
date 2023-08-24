package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.option.dto.DefaultOptionSummary;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.enums.OptionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OptionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Option> optionRowMapper = new OptionRowMapper();
    private final RowMapper<DefaultOptionSummary> defaultOptionSummaryRowMapper = new DefaultOptionSummaryRowMapper();

    public List<Option> findRequiredOptionsByTrimIdAndCategory(Long trimId, RequiredCategory name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", name.getName());
        params.addValue("trimId", trimId);
        params.addValue("optionType", OptionType.REQUIRED.getType());

        return namedParameterJdbcTemplate.query("select * from options " +
                        "join category on options.category_id = category.id and category.name = (:categoryName) " +
                        "join trim_options on options.id = trim_options.options_id " +
                        "and trim_options.trim_id = (:trimId) and trim_options.type = (:optionType) ",
                params, optionRowMapper);
    }

    public List<Option> findRequiredOptionsByTrimIdAndExteriorColorId(Long trimId, RequiredCategory category,
                                                                      Long exteriorColorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", category.getName());
        params.addValue("trimId", trimId);
        params.addValue("optionType", OptionType.REQUIRED.getType());
        params.addValue("exteriorColorId", exteriorColorId);

        String baseQuery = "select options.id as id, options.name as name, options.price as price, " +
                "options.feedback_title as feedback_title, options.feedback_description as feedback_description, " +
                "options.category_id as category_id from options " +
                "join category on options.category_id = category.id and category.name = (:categoryName) " +
                "join trim_options on options.id = trim_options.options_id " +
                "and trim_options.trim_id = (:trimId) and trim_options.type = (:optionType)";

        String fullQuery = baseQuery +
                " join options_relation on options.id = options_relation.child_id " +
                "and options_relation.parent_id = (:exteriorColorId)";


        List<Option> query = namedParameterJdbcTemplate.query(fullQuery,
                params, optionRowMapper);
        if (query.isEmpty()) {
            return namedParameterJdbcTemplate.query(baseQuery,
                    params, optionRowMapper);
        }
        return query;
    }

    public List<Option> findByTrimIdAndOptionType(Long trimId, OptionType type) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("optionType", type.getType());

        String sql = "SELECT * FROM options " +
                "JOIN trim_options ON options.id = trim_options.options_id " +
                "AND trim_options.trim_id = (:trimId) " +
                "AND trim_options.type = (:optionType)";

        return namedParameterJdbcTemplate.query(sql,
                params, optionRowMapper);
    }

    public int countDefaultOptionsByTrimIdAndCategoryId(Long trimId, Long categoryId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimOptionType", OptionType.DEFAULT.getType());
        params.addValue("categoryId", categoryId);
        params.addValue("trimId", trimId);

        // 전체 카테고리 조회
        String sql = "SELECT COUNT(*) FROM trim " +
                "JOIN trim_options ON trim.id = trim_options.trim_id AND trim_options.type = (:trimOptionType) " +
                "JOIN options ON trim_options.options_id = options.id ";

        // 특정 카테고리 조회
        if(categoryId != 0) {
            sql += "AND options.category_id = (:categoryId) ";
        }
        sql += "WHERE trim.id = (:trimId)";

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

        if (count == null) {
            return 0;
        }
        return count;
    }

    public List<DefaultOptionSummary> findPaginatedDefaultOptionsByTrimIdAndCategoryId(Long trimId,
                                                                                       Long categoryId,
                                                                                       int page, int size) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimOptionType", OptionType.DEFAULT.getType());
        params.addValue("categoryId", categoryId);
        params.addValue("trimId", trimId);
        params.addValue("size", size);
        params.addValue("offset", (page - 1) * size);

        String sql = "SELECT " +
                "options.id AS optionsId, " +
                "options.name AS optionsName, " +
                "options.category_id AS optionsCategoryId, " +
                "options_image.img_url AS optionsImgUrl " +
                "FROM trim " +
                "JOIN trim_options ON trim.id = trim_options.trim_id AND trim_options.type = (:trimOptionType) " +
                "JOIN options ON trim_options.options_id = options.id ";

        // 특정 카테고리 조회
        if(categoryId != 0) {
            sql += "AND options.category_id = (:categoryId) ";
        }

        sql += "LEFT JOIN options_image ON options.id = options_image.options_id " +
                "WHERE trim.id = (:trimId) " +
                "ORDER BY options.id LIMIT :size OFFSET :offset";

        return namedParameterJdbcTemplate.query(sql, params, defaultOptionSummaryRowMapper);
    }

    public Optional<Option> findByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        try {
            Option option = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM options WHERE name = :name", params, optionRowMapper);
            return Optional.ofNullable(option);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class OptionRowMapper implements RowMapper<Option> {
        @Override
        public Option mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Option.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .feedbackTitle(resultSet.getString("feedback_title"))
                    .feedbackDescription(resultSet.getString("feedback_description"))
                    .categoryId(resultSet.getLong("category_id"))
                    .build();
        }
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
