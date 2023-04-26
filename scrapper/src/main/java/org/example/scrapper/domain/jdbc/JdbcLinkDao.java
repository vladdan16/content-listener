package org.example.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.mapper.ChatRowMapper;
import org.example.scrapper.domain.mapper.LinkRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class JdbcLinkDao {
    private final JdbcTemplate jdbcTemplate;
    private final LinkRowMapper linkRowMapper;
    private final ChatRowMapper chatRowMapper;

    @Transactional
    public Link add(@NotNull String link, Long chatId) {
        Link existingLink = findLink(link);
        if (existingLink == null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT INTO link (link, time_created) VALUES (?, ?) RETURNING id";
            Long id = jdbcTemplate.queryForObject(sql, Long.class, link, timestamp);
            existingLink = new Link(id, link, timestamp, null, null);
        }
        if (ifChatLinkExist(chatId, existingLink.getId())) {
            throw new RuntimeException("Unable to track link, that already tracked");
        }
        String sql = "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, chatId, existingLink.getId());
        return existingLink;
    }

    @Transactional
    public Link remove(String link, Long chatId) {
        Link linkDto = findLink(link);
        if (linkDto == null) {
            throw new RuntimeException("Unable to remove non-existing link");
        }
        if (!ifChatLinkExist(chatId, linkDto.getId())) {
            throw new RuntimeException("Unable to remove link that was not tracked");
        }
        String sql = "DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?";
        jdbcTemplate.update(sql, chatId, linkDto.getId());
        if (findSubscribers(link).size() == 0) {
            sql = "DELETE FROM link WHERE link = ?";
            jdbcTemplate.update(sql, link);
        }
        return linkDto;
    }

    public List<Link> findAll() {
        String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

    @Transactional
    public void update(String link, Timestamp updatedAt) {
        String sql = "UPDATE link SET time_checked = NOW() WHERE link = ?";
        jdbcTemplate.update(sql, link);

        sql = "UPDATE link SET updated_at = ? WHERE link = ?";
        jdbcTemplate.update(sql, updatedAt, link);
    }

    public Link findLink(String link) {
        String sql = "SELECT * FROM link WHERE link.link = ?";
        List<Link> list = jdbcTemplate.query(sql, linkRowMapper, link);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Chat> findSubscribers(String link) {
        String sql = """
                SELECT * FROM chat
                INNER JOIN chat_link cl on chat.id = cl.chat_id
                INNER JOIN link l on l.id = cl.link_id
                WHERE l.link = ?
                """;
        return jdbcTemplate.query(sql, chatRowMapper, link);
    }

    public List<Link> findAllOldLinks(@NotNull String interval) {
        String sql = " SELECT * FROM link WHERE link.time_checked < NOW() - INTERVAL '" + interval + "' OR link.time_checked IS NULL";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

    private boolean ifChatLinkExist(long chatId, long linkId) {
        String sql = "SELECT COUNT(*) FROM chat_link WHERE chat_id = ? AND link_id = ?";
        long ans = jdbcTemplate.query(sql, ResultSet::getLong, chatId, linkId).get(0);
        return ans == 1;
    }
}