package org.example.scrapper.client;

import org.example.scrapper.dto.GithubResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GithubClient {

    private final WebClient webClient;

    public GithubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GithubResponse fetchRepository(String owner, String repo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .block();
    }
}

