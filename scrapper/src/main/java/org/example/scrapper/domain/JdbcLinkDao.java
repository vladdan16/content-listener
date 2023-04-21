package org.example.scrapper.domain;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;
import org.example.scrapper.domain.interfaces.LinkDao;
import org.example.scrapper.domain.mapper.ChatRowMapper;
import org.example.scrapper.domain.mapper.LinkRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkDao implements LinkDao {
    private final JdbcTemplate jdbcTemplate;
    private final LinkRowMapper linkRowMapper;
    private final ChatRowMapper chatRowMapper;

    @Override
    @Transactional
    public LinkDto add(@NotNull String link, Long chatId) {
        LinkDto existingLink = findLink(link);
        if (existingLink == null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT INTO link (link, time_created, time_checked) VALUES (?, ?, ?) RETURNING id";
            Long id = jdbcTemplate.queryForObject(sql, Long.class, link, timestamp, timestamp);
            existingLink = new LinkDto(id, link, timestamp, timestamp);
        }
        if (ifChatLinkExist(chatId, existingLink.getId())) {
            throw new RuntimeException("Unable to track link, that already tracked");
        }
        String sql = "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, chatId, existingLink.getId());
        return existingLink;
    }

    @Override
    @Transactional
    public LinkDto remove(String link, Long chatId) {
        LinkDto linkDto = findLink(link);
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

    @Override
    public List<LinkDto> findAll() {
        String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

    @Override
    public void update(String link) {
        String sql = "UPDATE link SET time_checked = NOW() WHERE link = ?";
        jdbcTemplate.update(sql, link);
    }

    @Override
    public LinkDto findLink(String link) {
        String sql = "SELECT * FROM link WHERE link.link = ?";
        List<LinkDto> list = jdbcTemplate.query(sql, linkRowMapper, link);
        return list.get(0);
    }

    @Override
    public List<ChatDto> findSubscribers(String link) {
        String sql = """
                SELECT * FROM chat
                INNER JOIN chat_link cl on chat.id = cl.chat_id
                INNER JOIN link l on l.id = cl.link_id
                WHERE l.link = ?
                """;
        return jdbcTemplate.query(sql, chatRowMapper, link);
    }

    private boolean ifChatLinkExist(long chatId, long linkId) {
        String sql = "SELECT count(*) FROM chat_link WHERE chat_id = ? AND link_id = ?";
        long ans = jdbcTemplate.query(sql, ResultSet::getLong, chatId, linkId).get(0);
        return ans == 1;
    }
}
