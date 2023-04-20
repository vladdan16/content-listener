package org.example.scrapper;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class DatabaseTest extends IntegrationEnvironment {
    private static final int TIMEOUT = 5;

    @Test
    public void testDBData() {
        setUpTestData();

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM chat WHERE id = 1")) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM link WHERE link = 'github.com'")) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
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
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO link (id, link, time_created, time_checked) VALUES (?, ?, ?, ?)")) {
            statement.setLong(1, 1L);
            statement.setString(2, "github.com");
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