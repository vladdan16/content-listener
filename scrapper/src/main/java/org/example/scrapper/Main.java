package org.example.scrapper;

import org.example.scrapper.client.GithubClient;
import org.example.scrapper.dto.GithubResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;


public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ScrapperApplication.class, args);
        GithubClient client = context.getBean(GithubClient.class);
        String owner = "vladdan16";
        String repo = "content-listener";
        GithubResponse response = client.fetchRepository(owner, repo);
        System.out.println(response.name());
        System.out.println(response.updatedAt());
    }
}
