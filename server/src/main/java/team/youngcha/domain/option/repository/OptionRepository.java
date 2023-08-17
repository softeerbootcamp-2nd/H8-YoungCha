package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.dto.DefaultOptionSummary;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.enums.OptionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Option> optionRowMapper = new OptionRowMapper();
    private final RowMapper<DefaultOptionSummary> defaultOptionSummaryRowMapper = new DefaultOptionSummaryRowMapper();

    public List<Option> findOptionsByTrimIdAndType(Long trimId, OptionType type, SelectiveCategory name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", name.getName());
        params.addValue("trimId", trimId);
        params.addValue("optionType", type.getType());

        return namedParameterJdbcTemplate.query("select * from options " +
                        "join category on options.category_id = category.id and category.name = (:categoryName) " +
                        "join trim_options on options.id = trim_options.options_id " +
                        "and trim_options.trim_id = (:trimId) and trim_options.type = (:optionType) ",
                params, optionRowMapper);
    }

    public List<Option> findInteriorColorsByTrimIdAndExteriorColorId(Long trimId, Long exteriorColorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", SelectiveCategory.INTERIOR_COLOR.getName());
        params.addValue("trimId", trimId);
        params.addValue("optionType", OptionType.OPTIONAL.getType());
        params.addValue("exteriorColorId", exteriorColorId);

        String baseQuery = "select options.id as id, options.name as name, options.price as price, " +
                "options.feedback as feedback, options.category_id as category_id from options " +
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
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimOptionType", OptionType.BASIC.getType());
        params.addValue("categoryId", categoryId);
        params.addValue("trimId", trimId);

        String sql = "SELECT COUNT(*) FROM trim " +
                "JOIN trim_options ON trim.id = trim_options.trim_id and trim_options.type = (:trimOptionType) " +
                "JOIN options ON trim_options.options_id = options.id and options.category_id = (:categoryId) " +
                "WHERE trim.id = (:trimId)";

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
        params.addValue("trimOptionType", OptionType.BASIC.getType());
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
                "JOIN options ON trim_options.options_id = options.id AND options.category_id = (:categoryId) " +
                "LEFT JOIN options_image ON options.id = options_image.options_id " +
                "WHERE trim.id = (:trimId) " +
                "ORDER BY options.id LIMIT :size OFFSET :offset";

        return namedParameterJdbcTemplate.query(sql, params, defaultOptionSummaryRowMapper);
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
