package org.example.scrapper.domain;

import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcLinkTest {

    @Autowired
    private JdbcLinkDao linkRepository;
    @Autowired
    private JdbcChatDao chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        ChatDto chat = new ChatDto(1L);
        LinkDto link = new LinkDto();
    }
}
