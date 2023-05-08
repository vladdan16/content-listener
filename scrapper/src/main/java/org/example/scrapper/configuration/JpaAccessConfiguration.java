package org.example.scrapper.configuration;

import org.example.scrapper.domain.jpa.ChatRepository;
import org.example.scrapper.domain.jpa.LinkRepository;
import org.example.scrapper.service.interfaces.ChatService;
import org.example.scrapper.service.interfaces.LinkService;
import org.example.scrapper.service.jpa.JpaChatService;
import org.example.scrapper.service.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(ChatRepository chatRepository, LinkRepository linkRepository) {
        return new JpaLinkService(chatRepository, linkRepository);
    }

    @Bean
    public ChatService chatService(ChatRepository chatRepository) {
        return new JpaChatService(chatRepository);
    }
}
