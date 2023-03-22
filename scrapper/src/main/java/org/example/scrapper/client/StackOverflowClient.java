package org.example.scrapper.client;

import org.example.scrapper.dto.StackOverflowResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverflowResponse fetchQuestion(Long questionId) {
        return webClient.get()
                .uri("/questions/{id}?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
    }
}
