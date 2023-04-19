package org.example.scrapper.domain;

import org.example.scrapper.IntegrationEnvironment;
import org.example.scrapper.configuration.ApplicationConfig;
import org.example.scrapper.domain.dto.ChatDto;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JdbcChatTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatDao chatRepository;
    @Autowired
    private ApplicationConfig applicationConfig;
    private final static String BASE_URL = "http://localhost:";

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        ChatDto chat = new ChatDto(1L);
        chatRepository.add(chat);
        List<ChatDto> chats = chatRepository.findAll();
        assertEquals(chats.size(), 1);
        assertEquals(chats.get(0).getId(), 1L);
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        ChatDto chat = new ChatDto(1L);
        chatRepository.add(chat);
        chatRepository.remove(1L);
        List<ChatDto> chats = chatRepository.findAll();
        assertEquals(chats.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        ChatDto chat1 = new ChatDto(1L);
        ChatDto chat2 = new ChatDto(2L);
        chatRepository.add(chat1);
        chatRepository.add(chat2);
        List<ChatDto> chats = chatRepository.findAll();
        assertEquals(chats.size(), 2);
    }
}
