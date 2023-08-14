package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionImage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionImageRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<OptionImage> optionImageRowMapper = new OptionImageRowMapper();

    public List<OptionImage> findByContainOptionIds(List<Long> optionIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("optionIds", optionIds);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        return namedParameterJdbcTemplate.query(
                "select * from options_image " +
                        "where options_image.options_id in (:optionIds) "
                , params, optionImageRowMapper);
    }

    private static class OptionImageRowMapper implements RowMapper<OptionImage> {
        @Override
        public OptionImage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return OptionImage.builder()
                    .id(resultSet.getLong("id"))
                    .imgUrl(resultSet.getString("img_url"))
                    .imgType(resultSet.getInt("img_type"))
                    .optionId(resultSet.getLong("options_id"))
                    .build();

        }
    }
}
