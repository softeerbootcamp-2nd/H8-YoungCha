package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.Estimate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EstimateRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Estimate> estimateRowMapper = BeanPropertyRowMapper.newInstance(Estimate.class);

    public Map<Long, Long> countPowerTrainByTrimIdAndContainPowerTrainIds(Long trimId, List<Long> powerTrainIds) {
        List<Map<String, Object>> results = querySellCounts(namedParameterJdbcTemplate, trimId, powerTrainIds);

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get("engine_id"),
                        row -> (Long) row.get("count")
                ));
    }

    private List<Map<String, Object>> querySellCounts(NamedParameterJdbcTemplate namedParameterJdbcTemplate, Long trimId, List<Long> powerTrainIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("powerTrainIds", powerTrainIds);

        return namedParameterJdbcTemplate.queryForList(
                "select sell.engine_id, COUNT(*) as count from sell " +
                        "where sell.engine_id in (:powerTrainIds) and sell.trim_id in (:trimId)" +
                        "group by sell.engine_id",
                params);
    }

    private static class EstimateRowMapper implements RowMapper<Estimate> {
        @Override
        public Estimate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Estimate.builder()
                    .id(resultSet.getLong("id"))
                    .trimId(resultSet.getLong("trim_id"))
                    .engineId(resultSet.getLong("engine_id"))
                    .bodyTypeId(resultSet.getLong("body_type_id"))
                    .drivingSystemId(resultSet.getLong("driving_system_id"))
                    .exteriorColorId(resultSet.getLong("exterior_color_id"))
                    .interiorColorId(resultSet.getLong("interior_color_id"))
                    .wheelId(resultSet.getLong("wheel_id"))
                    .keyword1Id(resultSet.getLong("keyword1_id"))
                    .keyword2Id(resultSet.getLong("keyword2_id"))
                    .keyword3Id(resultSet.getLong("keyword3_id"))
                    .ageRange(resultSet.getInt("age"))
                    .gender(resultSet.getInt("gender"))
                    .build();
        }
    }
}
