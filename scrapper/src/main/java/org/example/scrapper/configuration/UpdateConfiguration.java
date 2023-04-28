package org.example.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.client.BotClient;
import org.example.scrapper.client.ScrapperQueueProducer;
import org.example.scrapper.client.UpdateProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class UpdateConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public UpdateProcessor updateProcessor(RabbitTemplate rabbitTemplate, WebClient botWebClient) {
        if (applicationConfig.useQueue()) {
            return new ScrapperQueueProducer(rabbitTemplate);
        } else {
            return new BotClient(botWebClient);
        }
    }
}
