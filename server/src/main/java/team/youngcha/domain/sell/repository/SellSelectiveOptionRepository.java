package team.youngcha.domain.sell.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.sell.entity.SellSelectiveOption;

@Repository
@RequiredArgsConstructor
public class SellSelectiveOptionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SellSelectiveOption> sellSelectiveOptionRowMapper = BeanPropertyRowMapper.newInstance(SellSelectiveOption.class);
}
