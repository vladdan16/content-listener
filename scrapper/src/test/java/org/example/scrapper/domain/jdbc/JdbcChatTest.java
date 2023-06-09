package org.example.scrapper.domain.jdbc;

import org.example.scrapper.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcChatTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatDao chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        chatRepository.add(2L);
        List<Chat> chats = chatRepository.findAll();
        assertEquals(2, chats.size());
        assertEquals(2L, chats.get(2).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        chatRepository.add(2L);
        chatRepository.remove(2L);
        List<Chat> chats = chatRepository.findAll();
        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    public void findAllTest() {
        List<Chat> chats = chatRepository.findAll();
        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    public void findAllLinksByIdTest() {
        List<Link> links = chatRepository.findAllLinksById(1L);
        assertEquals(1, links.size());
    }

}
