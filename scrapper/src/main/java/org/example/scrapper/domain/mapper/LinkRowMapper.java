package org.example.scrapper.domain.mapper;

import org.example.scrapper.domain.dto.LinkDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


@Component
public class LinkRowMapper implements RowMapper<LinkDto> {
    @Override
    public LinkDto mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String link = rs.getString("link");
        Timestamp timeCreated = rs.getTimestamp("time_created");
        Timestamp timeChecked = rs.getTimestamp("time_checked");
        return new LinkDto(id, link, timeCreated, timeChecked);
    }
}