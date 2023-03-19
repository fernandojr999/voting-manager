package br.com.fernando.votingmanager.shared;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @BeforeEach
    public void init(){
        jdbcTemplate.execute("TRUNCATE TABLE tb_vote  RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE tb_meeting  RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE tb_user  RESTART IDENTITY CASCADE");
    }
}
