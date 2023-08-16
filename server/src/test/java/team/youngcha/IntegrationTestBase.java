package team.youngcha;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SoftAssertionsExtension.class)
public class IntegrationTestBase {

    @LocalServerPort
    public int port;

    @InjectSoftAssertions
    public SoftAssertions softAssertions;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @AfterEach
    void truncateDatabase() {
        Resource resource = new ClassPathResource("/data/clear.sql");
        try {
            String sqlScript = FileCopyUtils.copyToString(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            jdbcTemplate.execute(sqlScript);
        } catch (IOException e) {
            throw new RuntimeException("Error reading or executing SQL script: /data/clear.sql", e);
        }
    }

    public ExtractableResponse<Response> callEndpoint(String endpoint,
                                                      Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        return RestAssured
                .given().log().all().params(params)
                .when().get(endpoint)
                .then().log().all().extract();
    }
}
