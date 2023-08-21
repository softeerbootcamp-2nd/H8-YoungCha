package team.youngcha.domain.sell.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SellSelectiveOptionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Cacheable(value = "SelectiveOption", cacheManager = "mapCacheManager")
    public Map<Long, Long> countByOptionIdForTrim(Long trimId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);

        String sql = "SELECT options_id, COUNT(*) AS options_count " +
                "FROM sell_selective_options AS sso use index(options_id)" +
                "WHERE EXISTS(" +
                "SELECT 1 FROM sell AS s " +
                "WHERE sso.sell_id = s.id AND s.trim_id = (:trim_id))" +
                "GROUP BY options_id";

        return namedParameterJdbcTemplate.queryForList(sql, params)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get("options_id"),
                        row -> (Long) row.get("options_count")
                ));
    }
}
