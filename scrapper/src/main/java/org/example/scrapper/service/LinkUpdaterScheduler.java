package org.example.scrapper.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.linkparser.GithubParseResult;
import org.example.linkparser.ParseResult;
import org.example.linkparser.StackOverflowParseResult;
import org.example.linkparser.UrlParser;
import org.example.scrapper.client.BotClient;
import org.example.scrapper.client.GithubClient;
import org.example.scrapper.client.StackOverflowClient;
import org.example.scrapper.domain.jdbc.Chat;
import org.example.scrapper.domain.jdbc.JdbcLinkDao;
import org.example.scrapper.domain.jdbc.Link;
import org.example.scrapper.dto.responses.GithubResponse;
import org.example.scrapper.dto.responses.StackOverflowResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private UrlParser parser;
    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private final JdbcLinkDao linkRepository;

    private static final String INTERVAL = "1 hour";
    private static final String GITHUB_DESCRIPTION = "Update appeared at Github by the following link";
    private static final String STACKOVERFLOW_DESCRIPTION = "Update appeared at Stackoverflow by the following link";

    @PostConstruct
    public void setUrlParser() {
        parser = new UrlParser();
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        List<Link> list = linkRepository.findAllOldLinks(INTERVAL);
        for (Link link : list) {
            ParseResult result = parser.parseUrl(link.getLink());
            if (result == null) {
                System.out.println("Incorrect link type");
                continue;
            }
            switch (result.getLinkType()) {
                case "github" -> {
                    // parse link
                    GithubParseResult githubResult = (GithubParseResult) result;
                    // check for updates
                    GithubResponse githubResponse = githubClient.fetchRepository(githubResult.user(), githubResult.repo());
                    // process update
                    OffsetDateTime updatedAt = githubResponse.updatedAt();
                    if (link.getUpdatedAt() != null) {
                        OffsetDateTime lastUpdated = link.getUpdatedAt().toInstant().atOffset(updatedAt.getOffset());
                        link.setTimeChecked(new Timestamp(System.currentTimeMillis()));
                        link.setUpdatedAt(new Timestamp(updatedAt.toInstant().toEpochMilli()));
                        linkRepository.update(link.getLink(), link.getUpdatedAt());
                        if (updatedAt.isAfter(lastUpdated)) {
                            callBot(link, GITHUB_DESCRIPTION);
                        }
                    } else {
                        link.setTimeChecked(new Timestamp(System.currentTimeMillis()));
                        link.setUpdatedAt(new Timestamp(updatedAt.toInstant().toEpochMilli()));
                        linkRepository.update(link.getLink(), link.getUpdatedAt());
                        callBot(link, GITHUB_DESCRIPTION);
                    }
                }
                case "stackoverflow" -> {
                    // parse link
                    StackOverflowParseResult stackOverflowResult = (StackOverflowParseResult) result;
                    // check for updates
                    StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(stackOverflowResult.id());
                    // process update
                    OffsetDateTime updatedAt = stackOverflowResponse.updatedAt();
                    if (link.getUpdatedAt() != null) {
                        OffsetDateTime lastUpdated = link.getUpdatedAt().toInstant().atOffset(updatedAt.getOffset());
                        link.setTimeChecked(new Timestamp(System.currentTimeMillis()));
                        link.setUpdatedAt(new Timestamp(updatedAt.toInstant().toEpochMilli()));
                        linkRepository.update(link.getLink(), link.getUpdatedAt());
                        if (updatedAt.isAfter(lastUpdated)) {
                            callBot(link, STACKOVERFLOW_DESCRIPTION);
                        }
                    } else {
                        link.setTimeChecked(new Timestamp(System.currentTimeMillis()));
                        link.setUpdatedAt(new Timestamp(updatedAt.toInstant().toEpochMilli()));
                        linkRepository.update(link.getLink(), link.getUpdatedAt());
                        callBot(link, STACKOVERFLOW_DESCRIPTION);
                    }
                }
                default -> System.out.println("Unknown link");
            }
        }
    }

    private void callBot(Link link, String description) {
        List<Long> tgChatIds = linkRepository.findSubscribers(link.getLink())
                .stream()
                .map(Chat::getId)
                .toList();
        botClient.update(link.getId(), link.getLink(), description, tgChatIds);
    }
}
