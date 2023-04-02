package org.example.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.responses.GithubResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GithubClient {

    private final WebClient githubWebClient;

    public GithubResponse fetchRepository(String owner, String repo) {
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .block();
    }
}

