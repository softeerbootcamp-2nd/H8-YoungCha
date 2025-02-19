package team.youngcha.domain.sell.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.category.enums.RequiredCategory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SellRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Cacheable(value = "Sell", cacheManager = "longMapCacheManager")
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
    public Map<Long, Long> countOptionsByTrimIdAndAgeRange(Long trimId, RequiredCategory requiredCategory,
                                                           AgeRange ageRange) {
        String column = requiredCategory.getColumn() + "_id";

        String sql = "SELECT sell." + column + ", COUNT(*) AS count FROM sell " +
                "WHERE sell.age in (:ages) AND sell.trim_id = :trimId " +
                "GROUP BY sell." + column;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ages", ageRange.getAges());
        params.addValue("trimId", trimId);

        return namedParameterJdbcTemplate.queryForList(sql, params)
                .stream().collect(Collectors.toMap(
                        row -> (Long) row.get(column),
                        row -> (Long) row.get("count")
                ));
    }

    // 특정 트림의 특정 카테고리에 속하는 옵션의 성별별 판매량을 조회
    public Map<Long, Long> countOptionsByTrimIdAndGender(Long trimId, RequiredCategory requiredCategory,
                                                         Gender gender) {
        String column = requiredCategory.getColumn() + "_id";

        String sql = "SELECT sell." + column + ", COUNT(*) AS count FROM sell " +
                "WHERE sell.gender = :gender AND sell.trim_id = :trimId " +
                "GROUP BY sell." + column;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("gender", gender.getType());
        params.addValue("trimId", trimId);

        return namedParameterJdbcTemplate.queryForList(sql, params)
                .stream().collect(Collectors.toMap(
                        row -> (Long) row.get(column),
                        row -> (Long) row.get("count")
                ));
    }

    // 특정 트림의 특정 카테고리에 속하는 옵션의 연령대 및 성별 별 판매량을 조회
    public Map<Long, Long> countOptionsByTrimIdAndAgeRangeAndGender(Long trimId, RequiredCategory category,
                                                                    AgeRange ageRange, Gender gender) {
        String column = category.getColumn() + "_id";

        String sql = "SELECT sell." + column + ", COUNT(*) AS count FROM sell " +
                "WHERE sell.age in (:ages) " +
                "AND sell.gender = :gender AND sell.trim_id = :trimId " +
                "GROUP BY sell." + column;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ages", ageRange.getAges());
        params.addValue("gender", gender.getType());
        params.addValue("trimId", trimId);

        List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql, params);

        return result.stream().collect(Collectors.toMap(
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
        String index = "idx_" + category.getColumn() + "_trim";
        return namedParameterJdbcTemplate.queryForList(
                "select sell." + column + ", COUNT(*) as count " +
                        "from sell use index(" + index + ") " +
                        "where sell." + column + " in (:optionIds) and sell.trim_id = (:trimId) " +
                        "group by sell." + column,
                params);
    }
}
