package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.EstimateSelectiveOption;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EstimateSelectiveOptionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<EstimateSelectiveOption> estimateSelectiveOptionRowMapper
            = BeanPropertyRowMapper.newInstance(EstimateSelectiveOption.class);


    public Long countIfEstimateContainsOption(List<Long> estimateIds, Long optionId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("estimateIds", estimateIds);
        params.addValue("optionId", optionId);

        String sql = "SELECT COUNT(*) FROM estimate_selective_options e " +
                "WHERE e.estimate_id IN (:estimateIds) " +
                "AND e.options_id = :optionId";

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
