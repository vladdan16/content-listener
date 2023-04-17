package org.example.scrapper.domain;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.Dao.LinkDao;
import org.example.scrapper.domain.dto.LinkDto;
import org.example.scrapper.domain.mapper.LinkRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkDao implements LinkDao {
    private final JdbcTemplate jdbcTemplate;
    private final LinkRowMapper linkRowMapper;

    @Override
    @Transactional
    public void add(@NotNull LinkDto link) {
        String sql = "INSERT INTO link (id, link, owner_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, link.getId(), link.getLink(), link.getOwnerId());
    }

    @Override
    @Transactional
    public void remove(String link) {
        String sql = "DELETE FROM link WHERE link = ?";
        jdbcTemplate.update(sql, link);
    }

    @Override
    public List<LinkDto> findAll() {
        String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, linkRowMapper);
    }
}
