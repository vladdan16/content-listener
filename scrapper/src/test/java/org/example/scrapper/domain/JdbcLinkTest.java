package org.example.scrapper.domain;

import org.example.scrapper.IntegrationEnvironment;
import org.example.scrapper.domain.dto.ChatDto;
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
        ChatDto chat = new ChatDto(1L);
        LinkDto link = new LinkDto(1L, "github.com", 1L);
        chatRepository.add(chat);
        linkRepository.add(link);
        List<LinkDto> links = linkRepository.findAll();
        assertEquals(links.size(), 1);
        assertEquals(links.get(0).getLink(), "github.com");
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        ChatDto chat = new ChatDto(1L);
        LinkDto link = new LinkDto(1L, "github.com", 1L);
        chatRepository.add(chat);
        linkRepository.add(link);
        linkRepository.remove(link.getLink());
        List<LinkDto> links = linkRepository.findAll();
        assertEquals(links.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        ChatDto chat = new ChatDto(1L);
        LinkDto link1 = new LinkDto(1L, "github.com", 1L);
        LinkDto link2 = new LinkDto(1L, "stackoverflow.com", 1L);
        chatRepository.add(chat);
        linkRepository.add(link1);
        linkRepository.add(link2);
        List<LinkDto> links = linkRepository.findAll();
        assertEquals(links.size(), 2);
    }
}
