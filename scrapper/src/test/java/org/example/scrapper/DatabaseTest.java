package org.example.scrapper;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {
    @ClassRule
    public static PostgreSQLContainer<MyPostgresContainer> postgreSQLContainer = MyPostgresContainer.getInstance();

    @BeforeAll
    static void performMigrations() {
        postgreSQLContainer.setWaitStrategy(
                new LogMessageWaitStrategy()
                        .withRegEx(".*database system is ready to accept connections.*\\s")
                        .withTimes(1)
                        .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS))
        );
        postgreSQLContainer.start();
        Path migrationDir = new File("../../../../../migration/migrations").toPath();
        try {
            Liquibase liquibase = new Liquibase(migrationDir
                    .resolve("master.yaml")
                    .toString(),
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(
                            DriverManager.getConnection(
                                    postgreSQLContainer.getJdbcUrl(),
                                    postgreSQLContainer.getUsername(),
                                    postgreSQLContainer.getPassword()
                            )
                    )
            );
            liquibase.update("");
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
        setUpTestData();
    }

    @AfterAll
    static void stopContainer() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testDBConnection() {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        )) {
            assertTrue(connection.isValid(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDBData() {
        
    }

    private static void setUpTestData() {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        )) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO chat (id) VALUES (?)")) {
                statement.setLong(1, 1);
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO link_type (type) VALUES (?)")) {
                statement.setString(1, "test");
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO link (link, link_type_id, owner_id) VALUES (?, ?, ?)")) {
                statement.setString(1, "github.com");
                statement.setInt(2, 1);
                statement.setLong(3, 1);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
