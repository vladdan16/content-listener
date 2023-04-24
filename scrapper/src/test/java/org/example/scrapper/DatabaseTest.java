package org.example.scrapper;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class DatabaseTest extends IntegrationEnvironment {

    @Test
    public void testDBData() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM chat WHERE id = 1")) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM link WHERE link = ?")) {
            statement.setString(1, LINK);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}