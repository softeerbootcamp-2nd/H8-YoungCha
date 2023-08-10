package team.youngcha.domain.trim.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.trim.entity.Trim;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrimRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Trim> trimRowMapper = new TrimRowMapper();

    public Optional<Trim> findById(Long id) {
        List<Trim> trims = jdbcTemplate.query("select * from trim where id = ?", trimRowMapper, id);
        return trims.stream().findAny();
    }

    private static class TrimRowMapper implements RowMapper<Trim> {
        @Override
        public Trim mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Trim(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("background_img_url"),
                    resultSet.getString("car_img_url"),
                    resultSet.getString("hashtag"),
                    resultSet.getInt("price"),
                    resultSet.getString("description"),
                    resultSet.getLong("car_id")
            );
        }
    }
}
