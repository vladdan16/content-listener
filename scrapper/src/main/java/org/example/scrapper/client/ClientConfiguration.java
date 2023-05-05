package org.example.scrapper.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    /**
     * BAse url of bot.
     */
    @Value("${base-url}")
    private String baseUrl;

    /**
     * WebClient for GitHub.
     * @return WebClient instance
     */
    @Bean
    public WebClient githubWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * WebClient for StackOverflow.
     * @return WebClient instance
     */
    @Bean
    public WebClient stackOverflowWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.stackexchange.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * WebClient for Bot.
     * @return WebClient instance
     */
    @Bean
    public WebClient botWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
