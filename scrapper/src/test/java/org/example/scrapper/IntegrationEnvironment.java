package org.example.scrapper;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;

//@Testcontainers
public class IntegrationEnvironment {
    private static IntegrationEnvironment instance;
    public static PostgreSQLContainer<?> container;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public IntegrationEnvironment() {
        container = new PostgreSQLContainer<>("postgres:15.2-alpine")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        container.start();

        jdbcUrl = container.getJdbcUrl();
        username = container.getUsername();
        password = container.getPassword();
        //performMigrations();
    }

    private void performMigrations() {
        Path path = new File("/home/vladdan/Documents/GitHub/content-listener/migration/migrations")
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

    public static IntegrationEnvironment getInstance() {
        if (instance == null) {
            instance = new IntegrationEnvironment();
        }
        return instance;
    }

    public static PostgreSQLContainer<?> getContainer() {
        if (container == null) {
            getInstance();
        }
        return container;
    }

    public void stopContainer() {
        container.stop();
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
