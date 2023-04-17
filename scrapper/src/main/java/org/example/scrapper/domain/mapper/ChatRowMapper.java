package org.example.scrapper.domain.mapper;

import org.example.scrapper.domain.dto.ChatDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ChatRowMapper implements RowMapper<ChatDto> {

    @Override
    public ChatDto mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        return new ChatDto(id);
    }
}
