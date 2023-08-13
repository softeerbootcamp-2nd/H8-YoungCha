package team.youngcha.domain.keyword.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.keyword.entity.Keyword;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KeywordRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Keyword> keywordRowMapper = BeanPropertyRowMapper.newInstance(Keyword.class);

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
                                .name((String) map.get("name"))
                                .build(), Collectors.toList())
                ));
    }
}
