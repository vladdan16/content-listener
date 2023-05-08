package org.example.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.requests.LinkUpdateRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class ScrapperQueueProducer implements UpdateProcessor {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void update(LinkUpdateRequest updateRequest) {
        rabbitTemplate.convertAndSend(updateRequest);
    }
}
