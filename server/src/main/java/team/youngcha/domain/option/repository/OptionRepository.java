package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.Option;

@Repository
@RequiredArgsConstructor
public class OptionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Option> optionRowMapper = BeanPropertyRowMapper.newInstance(Option.class);
}
