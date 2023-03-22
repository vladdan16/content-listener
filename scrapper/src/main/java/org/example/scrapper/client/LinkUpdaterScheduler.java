package org.example.scrapper.client;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{app.scheduler.interval}")
    public void update() {
        System.out.println("Updating links...");
        // TODO: implement update logic
    }
}
