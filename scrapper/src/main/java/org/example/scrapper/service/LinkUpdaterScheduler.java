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
import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;
import org.example.scrapper.domain.interfaces.LinkDao;
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
    private final LinkDao linkRepository;

    private static final String INTERVAL = "1 minute";
    private static final String GITHUB_DESCRIPTION = "Update appeared at Github by the following link";
    private static final String STACKOVERFLOW_DESCRIPTION = "Update appeared at Stackoverflow by the following link";

    @PostConstruct
    public void setUrlParser() {
        parser = new UrlParser();
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        List<LinkDto> list = linkRepository.findAllOldLinks(INTERVAL);
        for (LinkDto link : list) {
            ParseResult result = parser.parseUrl(link.getLink());
            switch (result.getLinkType()) {
                case "github" -> {
                    // parse link
                    GithubParseResult githubResult = (GithubParseResult) result;
                    // check for updates
                    GithubResponse githubResponse = githubClient.fetchRepository(githubResult.user(), githubResult.repo());
                    // compare time
                    OffsetDateTime updatedAt = githubResponse.updatedAt();
                    OffsetDateTime lastUpdated = link.getUpdatedAt().toInstant().atOffset(updatedAt.getOffset());
                    link.setTimeChecked(new Timestamp(System.currentTimeMillis()));
                    link.setUpdatedAt(new Timestamp(updatedAt.toInstant().toEpochMilli()));
                    updateDB(link);
                    if (updatedAt.isAfter(lastUpdated)) {
                        callBot(link, GITHUB_DESCRIPTION);
                    }
                }
                case "stackoverflow" -> {
                    // parse link
                    StackOverflowParseResult stackOverflowResult = (StackOverflowParseResult) result;
                    // check for updates
                    StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(stackOverflowResult.id());
                    // compare time
                    OffsetDateTime updatedAt = stackOverflowResponse.updatedAt();
                    OffsetDateTime lastUpdated = link.getUpdatedAt().toInstant().atOffset(updatedAt.getOffset());
                    link.setTimeChecked(new Timestamp(System.currentTimeMillis()));
                    link.setUpdatedAt(new Timestamp(updatedAt.toInstant().toEpochMilli()));
                    updateDB(link);
                    if (updatedAt.isAfter(lastUpdated)) {
                        callBot(link, STACKOVERFLOW_DESCRIPTION);
                    }
                }
                default -> System.out.println("Unknown link");
            }
        }
    }

    private void callBot(LinkDto link, String description) {
        List<Long> tgChatIds = linkRepository.findSubscribers(link.getLink())
                .stream()
                .map(ChatDto::getId)
                .toList();
        botClient.update(link.getId(), link.getLink(), description, tgChatIds);
    }

    private void updateDB(LinkDto link) {
        linkRepository.update(link.getLink(), link.getUpdatedAt());
    }
}
