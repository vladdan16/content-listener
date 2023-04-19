package org.example.scrapper.domain;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.interfaces.ChatDao;
import org.example.scrapper.domain.mapper.ChatRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatDao implements ChatDao {
    private final JdbcTemplate jdbcTemplate;
    private final ChatRowMapper chatRowMapper;

    @Override
    @Transactional
    public void add(@NotNull ChatDto chat) {
        String sql = "INSERT INTO chat (id) VALUES (?)";
        jdbcTemplate.update(sql, chat.getId());
    }

    @Override
    @Transactional
    public void remove(long chatId) {
        String sql = "DELETE FROM chat WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    @Override
    public List<ChatDto> findAll() {
        String sql = "SELECT id FROM chat";
        return jdbcTemplate.query(sql, chatRowMapper);
    }
}
