package org.example.bot.controller;

import lombok.RequiredArgsConstructor;
import org.example.bot.controller.dto.LinkUpdateRequest;
import org.example.bot.core.MyBot;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final MyBot bot;

    @PostMapping("/updates")
    public void processLinkUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        bot.processUpdate(linkUpdateRequest.url(), linkUpdateRequest.descriptions(), linkUpdateRequest.tgChatIds());
    }
}
