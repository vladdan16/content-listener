package org.example.scrapper.domain.jpa;

import org.example.scrapper.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JpaLinkTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinkDao linkRepository;
    @Autowired
    private JpaChatDao chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        chatRepository.add(1L);
        linkRepository.add("github.com", 1L);
        List<Link> links = linkRepository.findAll();
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
        List<Link> links = linkRepository.findAll();
        assertEquals(links.size(), 0);
    }

    @Test
    @Transactional
    public void findAllTest() {
        List<Link> links = linkRepository.findAll();
        assertEquals(1, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Link linkDto = new Link();
        linkDto.setLink(LINK);
        linkDto.setTimeChecked(timestamp);
        linkDto.setUpdatedAt(timestamp);
        linkRepository.update(linkDto.getLink(), linkDto.getUpdatedAt());

        assertEquals(timestamp, linkDto.getUpdatedAt());
    }

    @Test
    @Transactional
    public void findSubscribersTest() {
        List<Chat> chats = linkRepository.findSubscribers(LINK);

        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    public void findAllOldLinksTest() throws InterruptedException {
        // Wait to be sure that 1 second passed since data was added
        Thread.sleep(1000);

        List<Link> links = linkRepository.findAllOldLinks("1 second");

        assertEquals(1, links.size());
    }
}
