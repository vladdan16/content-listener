package org.example.scrapper.configuration;

import org.example.scrapper.domain.jdbc.JdbcChatDao;
import org.example.scrapper.domain.jdbc.JdbcLinkDao;
import org.example.scrapper.service.interfaces.ChatService;
import org.example.scrapper.service.interfaces.LinkService;
import org.example.scrapper.service.jdbc.JdbcChatService;
import org.example.scrapper.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
            JdbcLinkDao jdbcLinkDao,
            JdbcChatDao jdbcChatDao
    ) {
        return new JdbcLinkService(jdbcLinkDao, jdbcChatDao);
    }

    @Bean
    public ChatService chatService(
            JdbcChatDao jdbcChatDao
    ) {
        return new JdbcChatService(jdbcChatDao);
    }
}
