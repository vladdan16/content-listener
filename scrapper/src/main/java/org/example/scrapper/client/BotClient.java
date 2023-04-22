package org.example.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.requests.LinkUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BotClient {
    private final WebClient botWebClient;

    public void update(long id, String url, String description, List<Long> tgChatIds) {
        try {
            LinkUpdateRequest request = new LinkUpdateRequest(id, new URI(url), description, tgChatIds);
            botWebClient.post()
                    .uri("/updates")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Incorrect url");
        }
    }
}
