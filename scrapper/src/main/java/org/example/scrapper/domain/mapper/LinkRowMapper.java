package org.example.scrapper.domain.mapper;

import org.example.scrapper.domain.dto.LinkDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LinkRowMapper implements RowMapper<LinkDto> {
    @Override
    public LinkDto mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String link = rs.getString("link");
        long ownerId = rs.getLong("owner_id");
        return new LinkDto(id, link, ownerId);
    }
}
