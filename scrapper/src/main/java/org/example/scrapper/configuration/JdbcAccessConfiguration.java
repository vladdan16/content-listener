package org.example.scrapper.configuration;

import org.example.scrapper.domain.jdbc.JdbcChatDao;
import org.example.scrapper.domain.jdbc.JdbcLinkDao;
import org.example.scrapper.domain.mapper.ChatRowMapper;
import org.example.scrapper.domain.mapper.LinkRowMapper;
import org.example.scrapper.service.interfaces.ChatService;
import org.example.scrapper.service.interfaces.LinkService;
import org.example.scrapper.service.jdbc.JdbcChatService;
import org.example.scrapper.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Bean
    public JdbcChatDao jdbcChatDao(
            JdbcTemplate jdbcTemplate,
            ChatRowMapper chatRowMapper,
            LinkRowMapper linkRowMapper
    ) {
        return new JdbcChatDao(jdbcTemplate, chatRowMapper, linkRowMapper);
    }

    @Bean
    public JdbcLinkDao jdbcLinkDao(
            JdbcTemplate jdbcTemplate,
            ChatRowMapper chatRowMapper,
            LinkRowMapper linkRowMapper
    ) {
        return new JdbcLinkDao(jdbcTemplate, linkRowMapper, chatRowMapper);
    }
}
