package team.youngcha.domain.car.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.car.entity.Car;
import team.youngcha.domain.category.enums.CategoryName;
import team.youngcha.domain.option.enums.OptionImageType;
import team.youngcha.domain.trim.enums.TrimOptionType;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Car> carRowMapper = BeanPropertyRowMapper.newInstance(Car.class);
    private final RowMapper<CarDetails> carDetailsDtoRowMapper = BeanPropertyRowMapper.newInstance(CarDetails.class);

    public Optional<Car> findById(Long carId) {
        String sql = "SELECT * FROM car WHERE id = ?";

        try {
            Car car = jdbcTemplate.queryForObject(sql, carRowMapper, carId);
            return Optional.ofNullable(car);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<CarDetails> findDetails(Long carId) {
        String sql =
                "SELECT * " +
                "FROM (" +
                        "SELECT " +
                            "car.id AS carId, " +
                            "car.name AS carName, " +
                            "trim.id AS trimId, " +
                            "trim.name AS trimName, " +
                            "trim.img_url AS trimImgUrl, " +
                            "trim.background_img_url AS trimBackgroundImgUrl, " +
                            "trim.hashtag AS trimHashTag, " +
                            "trim.price AS trimPrice, " +
                            "trim.description AS trimDescription, " +
                            "trim_options.type AS trimOptionType, " +
                            "options.name AS optionName, " +
                            "category.name AS optionCategoryName, " +
                            "options_image.img_url AS optionImgUrl, " +
                            "options_image.img_type AS optionImgType " +
                        "FROM car " +
                            "JOIN trim ON trim.car_id = car.id " +
                            "LEFT JOIN (SELECT * FROM trim_options WHERE NOT type = ?) trim_options ON trim.id = trim_options.trim_id " +
                            "LEFT JOIN options ON trim_options.options_id = options.id " +
                            "LEFT JOIN category ON options.category_id = category.id " +
                            "LEFT JOIN (SELECT * FROM options_image WHERE NOT img_type = ?) options_image ON options.id = options_image.options_id " +
                        "WHERE car.id = ?) inner_table " +
                "WHERE trimName = 'Guide Mode' " +
                        "OR trimOptionType = ? " +
                        "OR optionCategoryName = ? " +
                        "OR optionCategoryName = ? AND optionImgType = ?";

        return jdbcTemplate.query(sql, carDetailsDtoRowMapper,
                TrimOptionType.DEFAULT.getValue(),
                OptionImageType.LOGO.getValue(),
                carId,
                TrimOptionType.MAIN.getValue(),
                CategoryName.EXTERIOR_COLOR.getValue(),
                CategoryName.INTERIOR_COLOR.getValue(),
                OptionImageType.SUB.getValue());
    }
}