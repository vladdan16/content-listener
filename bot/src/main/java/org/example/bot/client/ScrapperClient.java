package org.example.bot.client;

import lombok.RequiredArgsConstructor;
import org.example.bot.client.dto.AddLinkRequest;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;
import org.example.bot.client.dto.RemoveLinkRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Scrapper client for bot module
 */
@RequiredArgsConstructor
public class ScrapperClient {
    private final WebClient scrapperWebClient;



    public ScrapperClient(String baseUrl) {
        scrapperWebClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void registerChat(long id) {
        scrapperWebClient.post()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteChat(long id) {
        scrapperWebClient.delete()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public ListLinksResponse getLinks(long id) {
        return scrapperWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("links")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    public LinkResponse addLink(long id, AddLinkRequest request) {
        return scrapperWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("links")
                        .queryParam("id", id)
                        .build())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public LinkResponse removeLink(long id, RemoveLinkRequest request) {
        return scrapperWebClient.method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("links")
                        .queryParam("id", id)
                        .build())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }
}
