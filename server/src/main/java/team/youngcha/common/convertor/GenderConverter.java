package team.youngcha.common.convertor;

import org.springframework.core.convert.converter.Converter;
import team.youngcha.common.enums.Gender;

public class GenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String typeStr) {
        return Gender.of(Integer.parseInt(typeStr));
    }
}