package team.youngcha.domain.car.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.option.enums.OptionImageType;
import team.youngcha.domain.option.enums.OptionType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarDetailsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<CarDetails> carDetailsDtoRowMapper = BeanPropertyRowMapper.newInstance(CarDetails.class);

    public List<CarDetails> findDetails(Long carId) {
        String sql = "SELECT " +
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
                "LEFT JOIN trim_options ON trim.id = trim_options.trim_id AND NOT trim_options.type = :defaultOption " +
                "LEFT JOIN options ON trim_options.options_id = options.id " +
                "LEFT JOIN category ON options.category_id = category.id " +
                "LEFT JOIN options_image ON options.id = options_image.options_id AND NOT options_image.img_type = :logo " +
                "WHERE car.id = :carId " +
                "AND trim.name = 'Guide Mode'" +
                "OR (trim_options.type = :mainOption AND options_image.img_type = :iconImage) " +
                "OR category.name = :exteriorColor " +
                "OR (category.name = :interiorColor AND options_image.img_type = :subImage)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("defaultOption", OptionType.BASIC.getType());
        params.addValue("logo", OptionImageType.LOGO.getValue());
        params.addValue("carId", carId);
        params.addValue("mainOption", OptionType.CORE.getType());
        params.addValue("exteriorColor", RequiredCategory.EXTERIOR_COLOR.getName());
        params.addValue("interiorColor", RequiredCategory.INTERIOR_COLOR.getName());
        params.addValue("subImage", OptionImageType.SUB.getValue());
        params.addValue("iconImage", OptionImageType.ICON.getValue());

        return namedParameterJdbcTemplate.query(sql, params, carDetailsDtoRowMapper);
    }
}
