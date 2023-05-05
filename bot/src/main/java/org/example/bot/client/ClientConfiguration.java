package org.example.bot.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    /**
     * Base url of scrapper.
     */
    @Value("${base-url}")
    private String baseUrl;

    /**
     * WebClient Bean for scrapper.
     * @return WebClient instance
     */
    @Bean
    public WebClient scrapperWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
