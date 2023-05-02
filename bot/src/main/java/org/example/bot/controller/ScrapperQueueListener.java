package org.example.bot.controller;

import lombok.RequiredArgsConstructor;
import org.example.bot.controller.dto.LinkUpdateRequest;
import org.example.bot.core.MyBot;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "${app.queue}")
public class ScrapperQueueListener {
    private final MyBot bot;
    @RabbitHandler
    public void receiver(@NotNull LinkUpdateRequest request) {
        bot.processUpdate(request.url(), request.descriptions(), request.tgChatIds());
    }
}
