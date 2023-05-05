package org.example.bot.client;

import lombok.RequiredArgsConstructor;
import org.example.bot.client.dto.AddLinkRequest;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;
import org.example.bot.client.dto.RemoveLinkRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Scrapper client for bot module.
 */
@Component
@RequiredArgsConstructor
public final class ScrapperClient {
    /**
     * Scrapper web client.
     */
    private final WebClient scrapperWebClient;
    private static final String LINKS = "links";
    private static final String PATH_SEGMENT = "/tg-chat/{id}";

    /**
     * Method to register chat in scrapper.
     * @param id telegram chat id
     */
    public void registerChat(final long id) {
        scrapperWebClient.post()
                .uri(PATH_SEGMENT, id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * Method to delete chat in scrapper.
     * @param id telegram chat id
     */
    public void deleteChat(final long id) {
        scrapperWebClient.delete()
                .uri(PATH_SEGMENT, id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * Method to get all tracked links by id.
     * @param id telegram chat id
     * @return ListLinkResponse
     */
    public ListLinksResponse getLINKS(final long id) {
        return scrapperWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(LINKS)
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    /**
     * Method to add link to track.
     * @param id telegram chat id
     * @param request AddLinkRequest
     * @return LinkResponse
     */
    public LinkResponse addLink(final long id, final AddLinkRequest request) {
        return scrapperWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(LINKS)
                        .queryParam("id", id)
                        .build())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    /**
     * Method to removed tracked link.
     * @param id telegram chat id
     * @param request RemoveLinkRequest
     * @return LinkResponse
     */
    public LinkResponse removeLink(final long id, final RemoveLinkRequest request) {
        return scrapperWebClient.method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(LINKS)
                        .queryParam("id", id)
                        .build())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }
}
