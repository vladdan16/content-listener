package org.example.scrapper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient githubWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public GithubClient githubClient(WebClient githubWebClient) {
        return new GithubClient(githubWebClient);
    }

    @Bean
    public WebClient stackOverflowWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.stackexchange.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public StackOverflowClient stackOverflowClient(WebClient stackOverflowWebClient) {
        return new StackOverflowClient(stackOverflowWebClient);
    }
}
