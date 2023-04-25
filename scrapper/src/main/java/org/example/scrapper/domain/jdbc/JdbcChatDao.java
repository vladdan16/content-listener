package org.example.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.mapper.ChatRowMapper;
import org.example.scrapper.domain.mapper.LinkRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class JdbcChatDao {
    private final JdbcTemplate jdbcTemplate;
    private final ChatRowMapper chatRowMapper;
    private final LinkRowMapper linkRowMapper;

    @Transactional
    public Chat add(@NotNull Long id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO chat (id, time_created) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql, id, timestamp);
        return new Chat(id, timestamp);
    }

    @Transactional
    public void remove(long chatId) {
        String sql = "DELETE FROM chat_link WHERE chat_id=?";
        jdbcTemplate.update(sql, chatId);
        sql = "DELETE FROM chat WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    public List<Chat> findAll() {
        String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql, chatRowMapper);
    }

    public List<Link> findAllLinksById(long chatId) {
        String sql = """
                SELECT * FROM link
                INNER JOIN chat_link cl on link.id = cl.link_id
                INNER JOIN chat c on c.id = cl.chat_id
                WHERE c.id = ?
                """;
        return jdbcTemplate.query(sql, linkRowMapper, chatId);
    }
}
