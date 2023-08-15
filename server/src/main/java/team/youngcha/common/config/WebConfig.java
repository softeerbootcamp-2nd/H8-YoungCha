package team.youngcha.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.youngcha.common.convertor.AgeRangeConverter;
import team.youngcha.common.convertor.GenderConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AgeRangeConverter());
        registry.addConverter(new GenderConverter());
    }
}
