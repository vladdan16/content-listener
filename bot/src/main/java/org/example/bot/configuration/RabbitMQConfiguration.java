package org.example.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfiguration {
    private final ApplicationConfig applicationConfig;
    private final static String DLQ = ".dlq";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(applicationConfig.exchange());
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(applicationConfig.queue()).build();
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue)
            .to(directExchange)
            .with(applicationConfig.routingKey());
    }

    @Bean
    public DirectExchange dlqDirectExchange() {
        return new DirectExchange("dlq-" + applicationConfig.exchange());
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(applicationConfig.queue() + DLQ)
            .withArgument("x-dead-letter-exchange", "")
            .withArgument("x-dead-letter-routing-key", applicationConfig.queue())
            .build();
    }

    @Bean
    public Binding dlqBinding(Queue dlqQueue, DirectExchange dlqDirectExchange) {
        return BindingBuilder.bind(dlqQueue)
            .to(dlqDirectExchange)
            .with(applicationConfig.routingKey() + DLQ);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
