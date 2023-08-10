package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.EstimateSelectiveOption;

@Repository
@RequiredArgsConstructor
public class EstimateSelectiveOptionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<EstimateSelectiveOption> estimateSelectiveOptionRowMapper = BeanPropertyRowMapper.newInstance(EstimateSelectiveOption.class);
}
