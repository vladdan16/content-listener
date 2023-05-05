package org.example.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.responses.GithubResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GithubClient {
    /**
     * GitHub WebClient.
     */
    private final WebClient githubWebClient;

    /**
     * Method to fetch repository.
     * @param owner username
     * @param repo repo name
     * @return GithubResponse
     */
    public GithubResponse fetchRepository(final String owner, final String repo) {
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .block();
    }
}

