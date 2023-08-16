package team.youngcha.domain.car.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.enums.OptionImageType;
import team.youngcha.domain.trim.enums.TrimOptionType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarDetailsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<CarDetails> carDetailsDtoRowMapper = BeanPropertyRowMapper.newInstance(CarDetails.class);

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
                        "LEFT JOIN trim_options ON trim.id = trim_options.trim_id AND NOT trim_options.type = ? " +
                        "LEFT JOIN options ON trim_options.options_id = options.id " +
                        "LEFT JOIN category ON options.category_id = category.id " +
                        "LEFT JOIN options_image ON options.id = options_image.options_id AND NOT options_image.img_type = ? " +
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
                SelectiveCategory.EXTERIOR_COLOR.getName(),
                SelectiveCategory.INTERIOR_COLOR.getName(),
                OptionImageType.SUB.getValue());
    }
}
