package team.youngcha.common.convertor;

import org.springframework.core.convert.converter.Converter;
import team.youngcha.common.type.AgeRange;

public class AgeRangeConverter implements Converter<String, AgeRange> {
    @Override
    public AgeRange convert(String rangeStr) {
        return AgeRange.of(Integer.parseInt(rangeStr));
    }
}
