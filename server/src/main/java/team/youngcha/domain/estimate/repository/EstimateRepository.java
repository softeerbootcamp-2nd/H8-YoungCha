package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.option.dto.GuideInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EstimateRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<Long, Long> countOptionsSimilarityUsers(Long trimId, List<Long> optionIds,
                                                       GuideInfo guideInfo, RequiredCategory category) {
        List<Map<String, Object>> results = queryCountSimilarityUser(namedParameterJdbcTemplate,
                trimId, optionIds, guideInfo, category);

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get(category.getColumn() + "_id"),
                        row -> (Long) row.get("count")
                ));
    }

    @Cacheable("Estimate")
    public int calculateRate(Long trimId, Long optionId, Long keywordId, RequiredCategory category) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("keywordId", keywordId);
        params.addValue("optionId", optionId);

        String column = category.getColumn() + "_id";

        // sum(트림 + 옵션 + 키워드 포함) * 100 / sum(트림 + 키워드 포함)
        Integer rate = namedParameterJdbcTemplate.queryForObject("SELECT " +
                        "(SUM(CASE WHEN " + column + " = (:optionId) THEN 1 ELSE 0 END) * 100) / COUNT(*) AS rate " +
                        "FROM estimate AS e use index(idx_keyword_trim)" +
                        "WHERE trim_id = (:trimId) AND (:keywordId) IN (e.keyword1_id, e.keyword2_id, e.keyword3_id);",
                params, Integer.class);
        if (rate == null) {
            return 0;
        }
        return rate;
    }

    private List<Map<String, Object>> queryCountSimilarityUser(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                               Long trimId, List<Long> optionIds,
                                                               GuideInfo guideInfo, RequiredCategory category) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("optionIds", optionIds);
        params.addValue("keywords", guideInfo.getKeywordIds());

        String optionIdColumn = category.getColumn() + "_id";

        String sql = "select estimate." + optionIdColumn + ", COUNT(*) as count from estimate " +
                "where estimate.keyword1_id in (:keywords) " +
                "and estimate.keyword2_id in (:keywords) " +
                "and estimate.keyword3_id in (:keywords) " +
                "and estimate.trim_id = (:trimId) ";

        if (!Objects.equals(category.getName(), RequiredCategory.WHEEL.getName())) {
            params.addValue("gender", guideInfo.getGender().getType());
            params.addValue("ageRange", guideInfo.getAgeRange().getRange());
            sql += "and estimate.age_range = (:ageRange) " +
                    "and estimate.gender = (:gender) ";
        }

        sql += "and estimate." + optionIdColumn + " in (:optionIds) " +
                "group by estimate." + optionIdColumn;

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public Map<Long, Integer> calculateSelectiveOptionsKeywordRate(Long trimId, List<Long> optionIds, Long keywordId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("optionIds", optionIds);
        params.addValue("keywordId", keywordId);

        String sql = "SELECT es.options_id, " +
                "COUNT(*) * 100 / (SELECT COUNT(*) FROM estimate e " +
                "WHERE :keywordId IN (e.keyword1_id, e.keyword2_id, e.keyword3_id)) AS rate " +
                "FROM estimate_selective_options es " +
                "JOIN estimate e ON e.id = es.estimate_id AND e.trim_id = :trimId " +
                "AND :keywordId IN (e.keyword1_id, e.keyword2_id, e.keyword3_id) " +
                "WHERE es.options_id IN (:optionIds) " +
                "GROUP BY es.options_id";

        return optionRateMapper(namedParameterJdbcTemplate.queryForList(sql, params));
    }

    public Map<Long, Integer> calculateSelectiveOptionsSimilarUserRate(List<Long> optionIds, List<Long> keywordIds, Long trimId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("optionIds", optionIds);
        params.addValue("trimId", trimId);
        params.addValue("keywordIds", keywordIds);

        String sql = "SELECT es.options_id, (count(*) * 100 / (select count(*) from estimate where keyword1_id in (:keywordIds) and keyword2_id in (:keywordIds) and keyword3_id in (:keywordIds))) as rate from estimate_selective_options es " +
                "join estimate e on es.estimate_id = e.id and e.trim_id = :trimId " +
                "and (e.keyword1_id in (:keywordIds) AND e.keyword2_id in (:keywordIds) AND e.keyword3_id in (:keywordIds)) " +
                "where es.options_id in (:optionIds) " +
                "group by es.options_id";

        return optionRateMapper(namedParameterJdbcTemplate.queryForList(sql, params));
    }

    private Map<Long, Integer> optionRateMapper(List<Map<String, Object>> resultSet) {
        Map<Long, Integer> resultMap = new HashMap<>();

        for (Map<String, Object> row : resultSet) {
            Long optionsId = (Long) row.get("options_id");
            BigDecimal rate = (BigDecimal) row.get("rate");
            int resultRate = rate != null ? rate.intValue() : 0;
            resultMap.put(optionsId, resultRate);
        }

        return resultMap;
    }
}
