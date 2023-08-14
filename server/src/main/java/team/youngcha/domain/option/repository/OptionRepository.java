package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Option> optionRowMapper = new OptionRowMapper();

    public List<Option> findOptionsByTrimIdAndType(Long trimId, OptionType type, SelectiveCategory name) {

        return jdbcTemplate.query("select * from options " +
                        "join category on options.category_id = category.id " +
                        "and category.name = '" + name.getName() + "' " +
                        "join trim_options on options.id = trim_options.options_id " +
                        "and trim_options.trim_id = ? and trim_options.type = ? ",
                optionRowMapper, trimId, type.getType());
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
