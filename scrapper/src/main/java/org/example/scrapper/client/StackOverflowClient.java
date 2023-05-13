package org.example.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.responses.StackOverflowResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public final class StackOverflowClient {
    /**
     * StackOverflow web client.
     */
    private final WebClient stackOverflowWebClient;

    /**
     * Method to fetch question.
     * @param questionId String question ID on StackOverflow
     * @return StackOverflow response
     */
    public StackOverflowResponse fetchQuestion(final String questionId) {
        return stackOverflowWebClient.get()
                .uri("/questions/{id}?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
    }
}
