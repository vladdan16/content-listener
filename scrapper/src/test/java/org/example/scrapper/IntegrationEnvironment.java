package org.example.scrapper;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationEnvironment {
    private static IntegrationEnvironment instance;
    public static PostgreSQLContainer<?> container;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    protected IntegrationEnvironment() {
        container = new PostgreSQLContainer<>("postgres:15.2-alpine")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        container.start();

        jdbcUrl = container.getJdbcUrl();
        username = container.getUsername();
        password = container.getPassword();

//        try {
//            Path path = new File("/home/vladdan/Documents/GitHub/content-listener/migration/migrations/master.yaml")
//                    .toPath();
//            Liquibase liquibase = new Liquibase(path.toString(),
//                    new ClassLoaderResourceAccessor(),
//                    new JdbcConnection(
//                            DriverManager.getConnection(
//                                    container.getJdbcUrl(),
//                                    container.getUsername(),
//                                    container.getPassword()
//                            )
//                    )
//            );
//            liquibase.update("");
//        } catch (LiquibaseException | SQLException e) {
//            throw new RuntimeException(e);
//        }
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