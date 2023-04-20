package org.example.scrapper;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class DatabaseTest extends IntegrationEnvironment {
    private static final int TIMEOUT = 5;
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(
                    container.getJdbcUrl(),
                    container.getUsername(),
                    container.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    public void testDBConnection() {
        System.out.println(container.getJdbcUrl());
        try {
            assertTrue(connection.isValid(TIMEOUT));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(2)
    public void testDBData() {
        setUpTestData();

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM chat WHERE id = 1")) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM link WHERE link = 'github.com' AND owner_id = 1")) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private static void setUpTestData() {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO chat (id) VALUES (?)")) {
            statement.setLong(1, 1);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO link (link, owner_id) VALUES (?, ?)")) {
            statement.setString(1, "github.com");
            statement.setLong(2, 1);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}