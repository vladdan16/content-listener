package org.example.scrapper;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.*;

@Testcontainers
public class IntegrationEnvironment {
    public static final PostgreSQLContainer<?> container;
    public static final Connection connection;

    static {
        container = new PostgreSQLContainer<>("postgres:15.2-alpine")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        container.start();
        performMigrations();

        try {
            connection = DriverManager.getConnection(
                    container.getJdbcUrl(),
                    container.getUsername(),
                    container.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpTestData();
    }

    private static void performMigrations() {
        Path path = new File("/home/vladdan/Documents/GitHub/content-listener/scrapper/migration/migrations")
                .toPath();
        try {
            Liquibase liquibase = new Liquibase("master.yaml",
                    new DirectoryResourceAccessor(path),
                    new JdbcConnection(
                            DriverManager.getConnection(
                                    container.getJdbcUrl(),
                                    container.getUsername(),
                                    container.getPassword()
                            )
                    )
            );
            liquibase.update("");
        } catch (LiquibaseException | SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setUpTestData() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO chat (id, time_created) VALUES (?, ?)")) {
            statement.setLong(1, 1L);
            statement.setTimestamp(2, timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO link (id, link, time_created, time_checked, updated_at) VALUES (?, ?, ?, ?, ?)")) {
            statement.setLong(1, 1L);
            statement.setString(2, "github.com/user/repo");
            statement.setTimestamp(3, timestamp);
            statement.setTimestamp(4, timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)")) {
            statement.setLong(1, 1L);
            statement.setLong(2, 1L);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
