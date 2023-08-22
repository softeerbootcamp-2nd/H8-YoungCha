package team.youngcha.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        "http://youngcha.team",
                        "http://youngcha.team:80",
                        "https://youngcha.team",
                        "https://youngcha.team:443")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
