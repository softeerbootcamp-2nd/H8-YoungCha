package team.youngcha.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.origin}")
    private String ORIGIN;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(ORIGIN);
        return new OpenAPI()
                .components(new Components())
                .servers(List.of(server))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("YoungCha")
                .description("H8 YoungCha API")
                .version("0.0.1");
    }
}
