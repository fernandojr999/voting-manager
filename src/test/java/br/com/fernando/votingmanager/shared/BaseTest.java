package br.com.fernando.votingmanager.shared;


import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest {

    /**
     * TEST-CONTAINERS
     */
    static PostgreSQLContainer<PostgresContainer> postgres;

    static {
        postgres = PostgresContainer.getInstance();
        postgres.start();
        System.out.println("POSTGRES PORT: " + postgres.getFirstMappedPort());
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
    }
}
