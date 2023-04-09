package org.example.scrapper;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MyPostgresContainer extends PostgreSQLContainer<MyPostgresContainer> {
    private static final String IMAGE_VERSION = "postgres:15.2-alpine";
    private static MyPostgresContainer container;

    private MyPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static MyPostgresContainer getInstance() {
        if (container == null) {
            container = new MyPostgresContainer()
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test")
                    .withExposedPorts(5432);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        // do nothing
    }
}
