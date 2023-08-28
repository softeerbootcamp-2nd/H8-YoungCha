package team.youngcha.domain.trim.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.trim.entity.TrimOption;

@Repository
@RequiredArgsConstructor
public class TrimOptionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TrimOption> trimOptionRowMapper = BeanPropertyRowMapper.newInstance(TrimOption.class);
}
