package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.Estimate;
import team.youngcha.domain.option.dto.GuideInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EstimateRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Estimate> estimateRowMapper = new EstimateRowMapper();

    public Map<Long, Long> countPowerTrainsSimilarityUsers(Long trimId, List<Long> powerTrainIds,
                                                           GuideInfo guideInfo) {
        List<Map<String, Object>> results = queryCountSimilarityUser(namedParameterJdbcTemplate,
                trimId, powerTrainIds, guideInfo);

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get("engine_id"),
                        row -> (Long) row.get("count")
                ));
    }

    public Integer calculateRate(Long trimId, Long powerTrainId, Long keywordId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("keywordId", keywordId);
        params.addValue("powerTrainId", powerTrainId);

        return namedParameterJdbcTemplate.queryForObject("select " +
                        "sum(case when " +
                        "e.trim_id = (:trimId) and e.engine_id = (:powerTrainId) " +
                        "and (e.keyword1_id = (:keywordId) or e.keyword2_id = (:keywordId) or e.keyword3_id = (:keywordId))" +
                        "then 1 else 0 end)" +
                        "/ sum(case when " +
                        "e.trim_id = (:trimId) and e.engine_id = (:powerTrainId) " +
                        "then 1 else 0 end) " +
                        "from estimate as e",
                params, Integer.class);
    }

    private List<Map<String, Object>> queryCountSimilarityUser(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                               Long trimId, List<Long> powerTrainIds, GuideInfo guideInfo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("powerTrainIds", powerTrainIds);
        params.addValue("gender", guideInfo.getGender().getType());
        params.addValue("ageRange", guideInfo.getAgeRange().getRange());
        params.addValue("keywords", guideInfo.getKeywordIds());

        return namedParameterJdbcTemplate.queryForList(
                "select estimate.engine_id, COUNT(*) as count from estimate " +
                        "where estimate.engine_id in (:powerTrainIds) " +
                        "and estimate.trim_id = (:trimId) " +
                        "and estimate.gender = (:gender) " +
                        "and estimate.age_range = (:ageRange) " +
                        "and estimate.keyword1_id in (:keywords) " +
                        "and estimate.keyword2_id in (:keywords) " +
                        "and estimate.keyword3_id in (:keywords) " +
                        "group by estimate.engine_id", params);
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
