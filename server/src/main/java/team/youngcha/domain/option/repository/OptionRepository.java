package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Option> optionRowMapper = new OptionRowMapper();

    public List<Option> findPowerTrainsByTrimIdAndType(Long trimId, OptionType type) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trim_id", trimId);
        params.addValue("type", type.getType());

        return jdbcTemplate.query("select * from options " +
                        "join category on options.category_id = category.id and category.name = '파워 트레인' " +
                        "join trim_options on options.id = trim_options.options_id " +
                        "and trim_options.trim_id = (:trimId) and trim_options.type = (:type) ",
                optionRowMapper, params);
    }

    private static class OptionRowMapper implements RowMapper<Option> {
        @Override
        public Option mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Option(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("feedback"),
                    resultSet.getLong("category_id")
            );
        }
    }
}
