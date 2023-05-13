package org.example.bot.controller;

import lombok.RequiredArgsConstructor;
import org.example.bot.controller.dto.LinkUpdateRequest;
import org.example.bot.core.MyBot;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final MyBot bot;

    /**
     * Method to process updates that scrapper sends to bot.
     * @param linkUpdateRequest Request from scrapper
     */
    @PostMapping("/updates")
    public void processLinkUpdate(@RequestBody @NotNull LinkUpdateRequest linkUpdateRequest) {
        bot.processUpdate(linkUpdateRequest.url(), linkUpdateRequest.descriptions(), linkUpdateRequest.tgChatIds());
    }
}
