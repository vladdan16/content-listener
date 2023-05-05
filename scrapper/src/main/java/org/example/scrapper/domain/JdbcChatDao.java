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
    /**
     * JdbcTemplate.
     */
    private final JdbcTemplate jdbcTemplate;
    /**
     * ChatRowMapper to map chat rows.
     */
    private final ChatRowMapper chatRowMapper;
    /**
     * LinkRowMapper to map link rows.
     */
    private final LinkRowMapper linkRowMapper;

    /**
     * Method that adds chat to repo.
     * @param id id of telegram chat
     * @return ChatDto object
     */
    @Override
    @Transactional
    public ChatDto add(@NotNull final Long id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO chat (id, time_created) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql, id, timestamp);
        return new ChatDto(id, timestamp);
    }

    /**
     * Method that removes chat from repo.
     * @param chatId id of telegram chat
     */
    @Override
    @Transactional
    public void remove(final long chatId) {
        String sql = "DELETE FROM chat_link WHERE chat_id=?";
        jdbcTemplate.update(sql, chatId);
        sql = "DELETE FROM chat WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    /**
     * Method that finds all chats in repository.
     * @return List of Chats
     */
    @Override
    public List<ChatDto> findAll() {
        String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql, chatRowMapper);
    }

    /**
     * Method that finds all links on which user subscribed.
     * @param chatId id of telegram chat
     * @return List of Links
     */
    @Override
    public List<LinkDto> findAllLinksById(final long chatId) {
        String sql = """
                SELECT * FROM link
                INNER JOIN chat_link cl on link.id = cl.link_id
                INNER JOIN chat c on c.id = cl.chat_id
                WHERE c.id = ?
                """;
        return jdbcTemplate.query(sql, linkRowMapper, chatId);
    }
}
