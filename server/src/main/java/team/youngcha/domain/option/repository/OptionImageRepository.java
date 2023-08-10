package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionImage;

@Repository
@RequiredArgsConstructor
public class OptionImageRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<OptionImage> optionImageRowMapper = BeanPropertyRowMapper.newInstance(OptionImage.class);
}
