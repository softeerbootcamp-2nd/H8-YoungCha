package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.enums.SelectiveCategory;
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

    public Map<Long, Long> countOptionsSimilarityUsers(Long trimId, List<Long> optionIds,
                                                       GuideInfo guideInfo, SelectiveCategory category) {
        List<Map<String, Object>> results = queryCountSimilarityUser(namedParameterJdbcTemplate,
                trimId, optionIds, guideInfo, category);

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get(category.getColumn() + "_id"),
                        row -> (Long) row.get("count")
                ));
    }

    public int calculateRate(Long trimId, Long optionId, Long keywordId, SelectiveCategory category) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("keywordId", keywordId);
        params.addValue("optionId", optionId);

        String optionIdColumn = category.getColumn() + "_id";

        // sum(트림 + 옵션 + 키워드 포함) * 100 / sum(트림 + 키워드 포함) -> 반올림
        Integer rate = namedParameterJdbcTemplate.queryForObject("select " +
                        "round(sum(case when " +
                        "e.trim_id = (:trimId) and e." + optionIdColumn + " = (:optionId) " +
                        "and (e.keyword1_id = (:keywordId) or e.keyword2_id = (:keywordId) or e.keyword3_id = (:keywordId))" +
                        "then 1 else 0 end) * 100" +
                        "/ sum(case when " +
                        "e.trim_id = (:trimId) " +
                        "and (e.keyword1_id = (:keywordId) or e.keyword2_id = (:keywordId) or e.keyword3_id = (:keywordId))" +
                        "then 1 else 0 end))" +
                        "from estimate as e",
                params, Integer.class);
        if (rate == null) {
            return 0;
        }
        return rate;
    }

    private List<Map<String, Object>> queryCountSimilarityUser(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                               Long trimId, List<Long> optionIds,
                                                               GuideInfo guideInfo, SelectiveCategory category) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("optionIds", optionIds);
        params.addValue("gender", guideInfo.getGender().getType());
        params.addValue("ageRange", guideInfo.getAgeRange().getRange());
        params.addValue("keywords", guideInfo.getKeywordIds());

        String optionIdColumn = category.getColumn() + "_id";

        return namedParameterJdbcTemplate.queryForList(
                "select estimate." + optionIdColumn + ", COUNT(*) as count from estimate " +
                        "where estimate." + optionIdColumn + " in (:optionIds) " +
                        "and estimate.trim_id = (:trimId) " +
                        "and estimate.gender = (:gender) " +
                        "and estimate.age_range = (:ageRange) " +
                        "and estimate.keyword1_id in (:keywords) " +
                        "and estimate.keyword2_id in (:keywords) " +
                        "and estimate.keyword3_id in (:keywords) " +
                        "group by estimate." + optionIdColumn, params);
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
