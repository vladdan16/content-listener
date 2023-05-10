package org.example.scrapper.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.linkparser.GithubParseResult;
import org.example.linkparser.ParseResult;
import org.example.linkparser.StackOverflowParseResult;
import org.example.linkparser.UrlParser;
import org.example.scrapper.client.BotClient;
import org.example.scrapper.client.GithubClient;
import org.example.scrapper.client.StackOverflowClient;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.GithubResponse;
import org.example.scrapper.dto.responses.StackOverflowResponse;
import org.example.scrapper.dto.responses.ChatResponse;
import org.example.scrapper.service.interfaces.LinkService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private UrlParser parser;
    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private final LinkService linkService;

    private static final String INTERVAL = "1 hour";
    private static final String GITHUB_DESCRIPTION = "Update appeared at Github by the following link";
    private static final String STACKOVERFLOW_DESCRIPTION = "Update appeared at Stackoverflow by the following link";

    /**
     * Method to set url parser after construction class instance.
     */
    @PostConstruct
    public void setUrlParser() {
        parser = new UrlParser();
    }

    /**
     * Method that looks old links every N seconds.
     */
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        ListLinksResponse list = linkService.findAllOldLinks(INTERVAL);
        for (LinkResponse link : list.links()) {
            ParseResult result = parser.parseUrl(link.url());
            if (result == null) {
                log.warn("Incorrect link type");
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
                    if (link.updatedAt() != null) {
                        OffsetDateTime lastUpdated = link.updatedAt().toInstant().atOffset(updatedAt.getOffset());
                        Timestamp updatedAtTimestamp =  new Timestamp(updatedAt.toInstant().toEpochMilli());
                        linkService.update(link.url(), updatedAtTimestamp);
                        if (updatedAt.isAfter(lastUpdated)) {
                            callBot(link, GITHUB_DESCRIPTION);
                        }
                    } else {
                        Timestamp updatedAtTimestamp =  new Timestamp(updatedAt.toInstant().toEpochMilli());
                        linkService.update(link.url(), updatedAtTimestamp);
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
                    if (link.updatedAt() != null) {
                        OffsetDateTime lastUpdated = link.updatedAt().toInstant().atOffset(updatedAt.getOffset());
                        Timestamp updatedAtTimestamp =  new Timestamp(updatedAt.toInstant().toEpochMilli());
                        linkService.update(link.url(), updatedAtTimestamp);
                        if (updatedAt.isAfter(lastUpdated)) {
                            callBot(link, STACKOVERFLOW_DESCRIPTION);
                        }
                    } else {
                        Timestamp updatedAtTimestamp =  new Timestamp(updatedAt.toInstant().toEpochMilli());
                        linkService.update(link.url(), updatedAtTimestamp);
                        callBot(link, STACKOVERFLOW_DESCRIPTION);
                    }
                }
                default -> log.warn("Unknown link");
            }
        }
    }

    private void callBot(LinkResponse link, String description) {
        List<Long> tgChatIds = linkService.findSubscribers(link.url()).chats()
                .stream()
                .map(ChatResponse::id)
                .toList();
        botClient.update(link.id(), link.url(), description, tgChatIds);
    }
}
