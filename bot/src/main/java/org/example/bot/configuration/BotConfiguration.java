package org.example.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BotConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public TelegramBot telegramBot() {
        String botToken = applicationConfig.token();
        return new TelegramBot(botToken);
    }
}
