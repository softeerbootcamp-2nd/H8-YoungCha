package team.youngcha.domain.option.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionDetail;
import team.youngcha.domain.option.entity.Spec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OptionDetailRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<OptionDetail> optionDetailRowMapper = new OptionDetailRowMapper();
    private final ResultSetExtractor<List<OptionDetail>> optionDetailResultSetExtractor = new OptionDetailResultSetExtractor();

    public List<OptionDetail> findWithSpecsByContainOptionIds(List<Long> optionIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("optionIds", optionIds);

        return namedParameterJdbcTemplate.query(
                "select od.id as id, od.name as name, od.description as description, od.img_url as img_url, od.options_id as options_id, " +
                        "s.id as spec_id, s.name as spec_name, s.description as spec_description, s.options_detail_id as spec_options_detail_id " +
                        "from options_detail as od " +
                        "left join spec as s on od.id = s.options_detail_id " +
                        "where od.options_id in (:optionIds) "
                , params, optionDetailResultSetExtractor);
    }

    private static class OptionDetailRowMapper implements RowMapper<OptionDetail> {
        @Override
        public OptionDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            return OptionDetail.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .imgUrl(rs.getString("img_url"))
                    .optionId(rs.getLong("options_id"))
                    .specs(new ArrayList<>())
                    .build();
        }
    }

    private static class OptionDetailResultSetExtractor implements ResultSetExtractor<List<OptionDetail>> {
        @Override
        public List<OptionDetail> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, OptionDetail> optionDetailMap = new LinkedHashMap<>();

            while (rs.next()) {
                Long optionDetailId = rs.getLong("id");
                OptionDetail optionDetail = optionDetailMap.get(optionDetailId);

                if (optionDetail == null) {
                    optionDetail = OptionDetail.builder()
                            .id(optionDetailId)
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .imgUrl(rs.getString("img_url"))
                            .optionId(rs.getLong("options_id"))
                            .specs(new ArrayList<>())
                            .build();

                    optionDetailMap.put(optionDetailId, optionDetail);
                }

                Long specId = (Long) rs.getObject("spec_id");
                if (specId != null) {
                    Spec spec = Spec.builder()
                            .id(specId)
                            .name(rs.getString("spec_name"))
                            .description(rs.getString("spec_description"))
                            .optionDetailId(rs.getLong("spec_options_detail_id"))
                            .build();
                    optionDetail.getSpecs().add(spec);
                }
            }
            return new ArrayList<>(optionDetailMap.values());
        }
    }
}
