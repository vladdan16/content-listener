package org.example.scrapper.domain.jpa;

import org.example.scrapper.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JpaChatTest extends IntegrationEnvironment {
    @Autowired
    private ChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Chat chat = new Chat();
        chat.setId(2L);
        chatRepository.save(chat);
        List<Chat> chats = chatRepository.findAll();
        assertEquals(2, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Chat chat = new Chat();
        chat.setId(2L);
        chat = chatRepository.save(chat);
        chatRepository.delete(chat);
        List<Chat> chats = chatRepository.findAll();
        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    public void findAllTest() {
        List<Chat> chats = chatRepository.findAll();
        assertEquals(1, chats.size());
    }
}
