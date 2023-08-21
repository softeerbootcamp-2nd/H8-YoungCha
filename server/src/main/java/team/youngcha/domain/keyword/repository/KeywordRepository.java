package team.youngcha.domain.keyword.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.keyword.entity.Keyword;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KeywordRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Keyword> keywordRowMapper = new KeywordRowMapper();

    public Optional<Keyword> findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            Keyword keyword = namedParameterJdbcTemplate.queryForObject(
                    "select * from keyword where id = :id",
                    params, keywordRowMapper);
            return Optional.ofNullable(keyword);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Keyword> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM keyword", keywordRowMapper);
    }

    public Map<Long, List<Keyword>> findByContainOptionIdsAndGroupKeywords(List<Long> optionIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("optionIds", optionIds);

        List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(
                "select ok.options_id as options_id, k.id as id, k.name as name " +
                        "from keyword as k " +
                        "join options_keyword as ok on k.id = ok.keyword_id " +
                        "and ok.options_id in (:optionIds)",
                params);

        return groupKeywordsByOptionsId(results);
    }

    private Map<Long, List<Keyword>> groupKeywordsByOptionsId(List<Map<String, Object>> maps) {
        return maps.stream()
                .collect(Collectors.groupingBy(
                        map -> (Long) map.get("options_id"),
                        Collectors.mapping(map -> Keyword.builder()
                                        .id((Long) map.get("id"))
                                        .name((String) map.get("name")).build(),
                                Collectors.toList())
                ));
    }

    private static class KeywordRowMapper implements RowMapper<Keyword> {
        @Override
        public Keyword mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Keyword.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .build();
        }
    }
}
