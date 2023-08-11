package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionDetailRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<OptionDetail> optionDetailRowMapper = new OptionDetailRowMapper();

    public List<OptionDetail> findByContainOptionIds(List<Long> optionIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("optionIds", optionIds);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        return namedParameterJdbcTemplate.query(
                "select * from options_detail " +
                        "where options_detail.options_id in (:optionIds) "
                , params, optionDetailRowMapper);
    }

    private static class OptionDetailRowMapper implements RowMapper<OptionDetail> {
        @Override
        public OptionDetail mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return OptionDetail.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .description(resultSet.getString("description"))
                    .imgUrl(resultSet.getString("img_url"))
                    .optionId(resultSet.getLong("options_id"))
                    .build();
        }
    }
}
