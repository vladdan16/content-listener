package org.example.scrapper.domain;

import org.example.scrapper.IntegrationEnvironment;
import org.example.scrapper.domain.dto.LinkDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private JdbcLinkDao linkRepository;
    @Autowired
    private JdbcChatDao chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        chatRepository.add(1L);
        linkRepository.add("github.com", 1L);
        List<LinkDto> links = linkRepository.findAll();
        assertEquals(links.size(), 1);
        assertEquals(links.get(0).getLink(), "github.com");
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        chatRepository.add(1L);
        linkRepository.add("github.com", 1L);
        linkRepository.remove("github.com", 1L);
        List<LinkDto> links = linkRepository.findAll();
        assertEquals(links.size(), 0);
    }
}
