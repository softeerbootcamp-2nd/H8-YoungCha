package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionDetail;

@Repository
@RequiredArgsConstructor
public class OptionDetailRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<OptionDetail> optionDetailRowMapper = BeanPropertyRowMapper.newInstance(OptionDetail.class);
}
