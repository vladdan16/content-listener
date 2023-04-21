package org.example.scrapper.domain;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;
import org.example.scrapper.domain.interfaces.ChatDao;
import org.example.scrapper.domain.mapper.ChatRowMapper;
import org.example.scrapper.domain.mapper.LinkRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatDao implements ChatDao {
    private final JdbcTemplate jdbcTemplate;
    private final ChatRowMapper chatRowMapper;
    private final LinkRowMapper linkRowMapper;

    @Override
    @Transactional
    public ChatDto add(@NotNull Long id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO chat (id, time_created) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql, id, timestamp);
        return new ChatDto(id, timestamp);
    }

    @Override
    @Transactional
    public void remove(long chatId) {
        String sql = "DELETE FROM chat_link WHERE chat_id=?";
        jdbcTemplate.update(sql, chatId);
        sql = "DELETE FROM chat WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    @Override
    public List<ChatDto> findAll() {
        String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql, chatRowMapper);
    }

    @Override
    public List<LinkDto> findAllLinksById(long chatId) {
        String sql = """
                SELECT * FROM link
                INNER JOIN chat_link cl on link.id = cl.link_id
                INNER JOIN chat c on c.id = cl.chat_id
                WHERE c.id = ?
                """;
        return jdbcTemplate.query(sql, linkRowMapper, chatId);
    }
}
