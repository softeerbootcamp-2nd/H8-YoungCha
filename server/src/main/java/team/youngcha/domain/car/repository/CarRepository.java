package team.youngcha.domain.car.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.car.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Car> carRowMapper = new CarRowMapper();

    public Optional<Car> findById(Long carId) {
        String sql = "SELECT * FROM car WHERE id = ?";

        try {
            Car car = jdbcTemplate.queryForObject(sql, carRowMapper, carId);
            return Optional.ofNullable(car);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Car> findAll() {
        return jdbcTemplate.query("SELECT * FROM car", carRowMapper);
    }

    private static class CarRowMapper implements RowMapper<Car> {
        @Override
        public Car mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Car(resultSet.getLong("id"),
                    resultSet.getString("name_ko"),
                    resultSet.getString("name_en"));
        }
    }
}
