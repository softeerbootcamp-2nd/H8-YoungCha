package team.youngcha.domain.sell.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.sell.entity.Sell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SellRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Sell> sellRowMapper = new SellRowMapper();

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

    private static class SellRowMapper implements RowMapper<Sell> {
        @Override
        public Sell mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Sell.builder()
                    .id(resultSet.getLong("id"))
                    .trimId(resultSet.getLong("trim_id"))
                    .engineId(resultSet.getLong("engine_id"))
                    .bodyTypeId(resultSet.getLong("body_type_id"))
                    .drivingSystemId(resultSet.getLong("driving_system_id"))
                    .exteriorColorId(resultSet.getLong("exterior_color_id"))
                    .interiorColorId(resultSet.getLong("interior_color_id"))
                    .wheelId(resultSet.getLong("wheel_id"))
                    .age(resultSet.getInt("age"))
                    .gender(resultSet.getInt("gender"))
                    .build();
        }
    }
}
