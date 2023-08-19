package team.youngcha.domain.sell.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SellSelectiveOptionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<Long, Long> countByOptionIdForTrim(Long trimId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("trimId", trimId);

        String sql = "SELECT options_id, COUNT(*) AS options_count " +
                "FROM sell_selective_options " +
                "WHERE sell_id IN (SELECT id FROM sell WHERE trim_id = (:trimId)) " +
                "GROUP BY options_id";

        return namedParameterJdbcTemplate.queryForList(sql, params)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row.get("options_id"),
                        row -> (Long) row.get("options_count")
                ));
    }
}
