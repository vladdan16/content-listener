package org.example.scrapper.domain;

import org.example.scrapper.IntegrationEnvironment;
import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private JdbcLinkDao linkRepository;
    @Autowired
    private JdbcChatDao chatRepository;

    private final static String LINK = "github.com/user/repo";

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

    @Test
    @Transactional
    public void findAllTest() {
        List<LinkDto> links = linkRepository.findAll();
        assertEquals(1, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        linkRepository.update(LINK, timestamp);

        LinkDto linkDto = linkRepository.findLink(LINK);
        assertEquals(timestamp, linkDto.getUpdatedAt());
    }

    @Test
    @Transactional
    public void findLinkTest() {
        LinkDto linkDto = linkRepository.findLink(LINK);

        assertEquals(LINK, linkDto.getLink());
    }

    @Test
    @Transactional
    public void findSubscribersTest() {
        List<ChatDto> chats = linkRepository.findSubscribers(LINK);

        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    public void findAllOldLinksTest() throws InterruptedException {
        // Wait to be sure that 1 second passed since data was added
        Thread.sleep(1000);

        List<LinkDto> links = linkRepository.findAllOldLinks("1 second");

        assertEquals(1, links.size());
    }
}
