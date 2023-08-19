package team.youngcha.domain.sell.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.domain.category.enums.RequiredCategory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SellRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<Long, Long> countOptionsByTrimIdAndContainOptionsIds(Long trimId, List<Long> optionIds,
                                                                    RequiredCategory category) {
        List<Map<String, Object>> results = querySellCounts(namedParameterJdbcTemplate, trimId,
                optionIds, category);

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get(category.getColumn() + "_id"),
                        row -> (Long) row.get("count")
                ));
    }

    // 특정 트림의 특정 카테고리에 속하는 옵션의 연령대별 판매량을 조회
    public Map<Long, Long> countOptionsByTrimIdAndAgeRange(Long trimId, RequiredCategory requiredCategory, AgeRange ageRange) {
        String column = requiredCategory.getColumn() + "_id";

        String sql = "SELECT sell." + column + ", COUNT(*) AS count FROM sell " +
                "WHERE (sell.age >= :minAge AND sell.age <= :maxAge) AND sell.trim_id = :trimId " +
                "GROUP BY sell." + column;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("minAge", ageRange.getMinAge());
        params.addValue("maxAge", ageRange.getMaxAge());
        params.addValue("trimId", trimId);

        return namedParameterJdbcTemplate.queryForList(sql, params)
                .stream().collect(Collectors.toMap(
                        row -> (Long) row.get(column),
                        row -> (Long) row.get("count")
                ));
    }

    private List<Map<String, Object>> querySellCounts(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                                      Long trimId, List<Long> optionIds,
                                                      RequiredCategory category) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);
        params.addValue("optionIds", optionIds);

        String column = category.getColumn() + "_id";
        return namedParameterJdbcTemplate.queryForList(
                "select sell." + column + ", COUNT(*) as count from sell " +
                        "where sell." + column + " in (:optionIds) and sell.trim_id = (:trimId) " +
                        "group by sell." + column,
                params);
    }
}
