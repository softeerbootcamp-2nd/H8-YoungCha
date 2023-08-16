package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Option> optionRowMapper = new OptionRowMapper();

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
}
