package org.example.scrapper.domain;

import org.example.scrapper.IntegrationEnvironment;
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

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        chatRepository.add(1L);
        List<ChatDto> chats = chatRepository.findAll();
        assertEquals(chats.size(), 1);
        assertEquals(chats.get(0).getId(), 1L);
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        chatRepository.add(1L);
        chatRepository.remove(1L);
        List<ChatDto> chats = chatRepository.findAll();
        assertEquals(chats.size(), 0);
    }

}
