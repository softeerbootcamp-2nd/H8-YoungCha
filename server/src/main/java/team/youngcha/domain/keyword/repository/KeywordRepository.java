package team.youngcha.domain.keyword.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.keyword.entity.Keyword;

@Repository
@RequiredArgsConstructor
public class KeywordRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Keyword> keywordRowMapper = BeanPropertyRowMapper.newInstance(Keyword.class);
}
