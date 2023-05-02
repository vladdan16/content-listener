package org.example.bot.controller;

import lombok.RequiredArgsConstructor;
import org.example.bot.controller.dto.LinkUpdateRequest;
import org.example.bot.core.MyBot;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class BotController {
    private final MyBot bot;

    @PostMapping("/updates")
    public void processLinkUpdate(@RequestBody @NotNull LinkUpdateRequest linkUpdateRequest) {
        bot.processUpdate(linkUpdateRequest.url(), linkUpdateRequest.descriptions(), linkUpdateRequest.tgChatIds());
    }
}
