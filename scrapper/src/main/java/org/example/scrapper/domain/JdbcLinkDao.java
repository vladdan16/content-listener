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
     * Method that adds Link to repo.
     * @param link String value of link
     * @param chatId id of telegram chat
     * @return Link that was added
     */
    @Override
    @Transactional
    public LinkDto add(@NotNull final String link, final Long chatId) {
        LinkDto existingLink = findLink(link);
        if (existingLink == null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT INTO link (link, time_created) VALUES (?, ?) RETURNING id";
            Long id = jdbcTemplate.queryForObject(sql, Long.class, link, timestamp);
            existingLink = new LinkDto(id, link, timestamp, null, null);
        }
        if (ifChatLinkExist(chatId, existingLink.getId())) {
            throw new RuntimeException("Unable to track link, that already tracked");
        }
        String sql = "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, chatId, existingLink.getId());
        return existingLink;
    }

    /**
     * Method that removes links from repo.
     * @param link String value of link
     * @param chatId id of telegram chat
     * @return Link that was removed
     */
    @Override
    @Transactional
    public LinkDto remove(final String link, final Long chatId) {
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

    /**
     * Method that finds all links in repo.
     * @return List of Links
     */
    @Override
    public List<LinkDto> findAll() {
        String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

    /**
     * Method that updates link in repo.
     * @param link String value of link
     */
    @Override
    public void update(final LinkDto link) {
        String sql = "UPDATE link SET time_checked = ? WHERE link = ?";
        jdbcTemplate.update(sql, link.getTimeChecked(), link.getLink());

        sql = "UPDATE link SET updated_at = ? WHERE link = ?";
        jdbcTemplate.update(sql, link.getUpdatedAt(), link.getLink());
    }

    /**
     * Method that finds link in repo by String value.
     * @param link String value of link
     * @return Link object
     */
    @Override
    public LinkDto findLink(final String link) {
        String sql = "SELECT * FROM link WHERE link.link = ?";
        List<LinkDto> list = jdbcTemplate.query(sql, linkRowMapper, link);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Method that finds all Chats that subscribed to specified link.
     * @param link String value of link
     * @return List of Chat objects
     */
    @Override
    public List<ChatDto> findSubscribers(final String link) {
        String sql = """
                SELECT * FROM chat
                INNER JOIN chat_link cl on chat.id = cl.chat_id
                INNER JOIN link l on l.id = cl.link_id
                WHERE l.link = ?
                """;
        return jdbcTemplate.query(sql, chatRowMapper, link);
    }

    /**
     * Method that finds all old links, for example those which have not been checked for 1 minute.
     * @return List of Links
     */
    @Override
    public List<LinkDto> findAllOldLinks(@NotNull final String interval) {
        String sql = " SELECT * FROM link WHERE link.time_checked < NOW() - INTERVAL '" + interval + "' OR link.time_checked IS NULL ";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

    private boolean ifChatLinkExist(final long chatId, final long linkId) {
        String sql = "SELECT count(*) FROM chat_link WHERE chat_id = ? AND link_id = ?";
        long ans = jdbcTemplate.query(sql, ResultSet::getLong, chatId, linkId).get(0);
        return ans == 1;
    }
}
