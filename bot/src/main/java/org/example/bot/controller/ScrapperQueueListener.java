package org.example.bot.controller;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.example.bot.controller.dto.LinkUpdateRequest;
import org.example.bot.core.MyBot;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "${app.queue}")
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class ScrapperQueueListener {
    private final MyBot bot;
    private final RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void receiver(@NotNull LinkUpdateRequest request, Channel channel, long tag) throws IOException {
        try {
            bot.processUpdate(request.url(), request.descriptions(), request.tgChatIds());
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, false);
            rabbitTemplate.convertAndSend("dlq-exchange", "my-queue.dlq", request);
        }
    }
}
