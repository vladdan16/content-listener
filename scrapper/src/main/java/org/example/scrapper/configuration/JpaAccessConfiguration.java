package org.example.scrapper.configuration;

import org.example.scrapper.domain.jpa.JpaChatDao;
import org.example.scrapper.domain.jpa.JpaLinkDao;
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
    public LinkService linkService(JpaLinkDao jpaLinkDao) {
        return new JpaLinkService(jpaLinkDao);
    }

    @Bean
    public ChatService chatService(JpaChatDao jpaChatDao) {
        return new JpaChatService(jpaChatDao);
    }
}
