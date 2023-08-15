package team.youngcha;

import io.restassured.RestAssured;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SoftAssertionsExtension.class)
public class IntegrationTestBase {

    @LocalServerPort
    public int port;

    @InjectSoftAssertions
    public SoftAssertions softAssertions;

    @Autowired
    DataSource dataSource;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @AfterEach
    void truncateDatabase() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("/data/clear.sql"));
    }
}
