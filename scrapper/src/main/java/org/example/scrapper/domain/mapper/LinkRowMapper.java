package org.example.scrapper.domain.mapper;

import org.example.scrapper.domain.jdbc.Link;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


@Component
public class LinkRowMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String link = rs.getString("link");
        Timestamp timeCreated = rs.getTimestamp("time_created");
        Timestamp timeChecked = rs.getTimestamp("time_checked");
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        return new Link(id, link, timeCreated, timeChecked, updatedAt);
    }
}
