package team.youngcha.domain.dictionary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.dictionary.entity.Dictionary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DictionaryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Dictionary> dictionaryRowMapper = new DictionaryRowMapper();

    public List<Dictionary> findAll() {
        return jdbcTemplate.query("select * from dictionary", dictionaryRowMapper);
    }

    private static class DictionaryRowMapper implements RowMapper<Dictionary> {
        @Override
        public Dictionary mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Dictionary.builder()
                    .id(resultSet.getLong("id"))
                    .word(resultSet.getString("word"))
                    .description(resultSet.getString("description"))
                    .imgUrl(resultSet.getString("img_url"))
                    .build();
        }
    }
}
