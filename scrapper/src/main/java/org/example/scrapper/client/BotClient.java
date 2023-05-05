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
    /**
     * WebClient for bot.
     */
    private final WebClient botWebClient;

    /**
     * Method to send update to user via bot.
     * @param id linkId
     * @param url link
     * @param description short description about update
     * @param tgChatIds list of IDs
     */
    public void update(final long id, final String url, final String description, final List<Long> tgChatIds) {
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
