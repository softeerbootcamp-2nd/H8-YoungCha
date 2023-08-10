package team.youngcha.domain.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.Estimate;

@Repository
@RequiredArgsConstructor
public class EstimateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Estimate> estimateRowMapper = BeanPropertyRowMapper.newInstance(Estimate.class);
}
