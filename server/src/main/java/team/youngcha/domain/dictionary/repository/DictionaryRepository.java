package team.youngcha.domain.dictionary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.dictionary.entity.Dictionary;

@Repository
@RequiredArgsConstructor
public class DictionaryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Dictionary> dictionaryRowMapper = BeanPropertyRowMapper.newInstance(Dictionary.class);
}
