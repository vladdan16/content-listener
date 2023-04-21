package org.example.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.client.GithubClient;
import org.example.scrapper.client.StackOverflowClient;
import org.example.scrapper.domain.dto.LinkDto;
import org.example.scrapper.domain.interfaces.ChatDao;
import org.example.scrapper.domain.interfaces.LinkDao;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;
    private final ChatDao chatRepository;
    private final LinkDao linkRepository;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        List<LinkDto> list = linkRepository.findAllOldLinks();
        for (LinkDto link : list) {
            // process each link
        }
    }
}
