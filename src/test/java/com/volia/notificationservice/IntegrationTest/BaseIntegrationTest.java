package com.volia.notificationservice.IntegrationTest;


import com.volia.notificationservice.NotificationServiceApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = NotificationServiceApplication.class)
@Sql(scripts = {"/sql/initTestData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
public class BaseIntegrationTest {


    @BeforeAll
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost/v1";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // MariaDb has been used because SQL test container is not compatible with Macbook M1
    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {
        MariaDBContainer<?> container = new MariaDBContainer<>("mariadb:10.6.4-focal")
                .withDatabaseName("volia")
                .withUsername("test")
                .withPassword("test");
        dynamicPropertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", container::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class", () -> "org.mariadb.jdbc.Driver");
        dynamicPropertyRegistry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MariaDBDialect");
        container.start();
    }
}
