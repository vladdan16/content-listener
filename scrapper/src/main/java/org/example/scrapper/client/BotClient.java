package org.example.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.requests.LinkUpdateRequest;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class BotClient implements UpdateProcessor {
    private final WebClient botWebClient;

    @Override
    public void update(LinkUpdateRequest request) {
        botWebClient.post()
                .uri("/updates")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
