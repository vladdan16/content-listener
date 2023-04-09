package org.example.scrapper;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
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
    @Order(1)
    public void testDBConnection() {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        )) {
            System.out.println(postgreSQLContainer.getJdbcUrl());
            assertTrue(connection.isValid(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testDBData() {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        )) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM chat WHERE id = 1")) {
                ResultSet resultSet = statement.executeQuery();

                resultSet.next();
                assertEquals(1, resultSet.getInt(1));
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM link_type WHERE type = 'test'")) {
                ResultSet resultSet = statement.executeQuery();

                resultSet.next();
                assertEquals(1, resultSet.getInt(1));
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM link WHERE link = 'github.com' AND link_type_id = 1 AND owner_id = 1")) {
                ResultSet resultSet = statement.executeQuery();

                resultSet.next();
                assertEquals(1, resultSet.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
