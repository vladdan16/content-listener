package org.example.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BotConfiguration {
    /**
     * Configuration of Application.
     */
    private final ApplicationConfig applicationConfig;

    /**
     * Bean for creating telegram bot.
     * @return TelegramBot instance
     */
    @Bean
    public TelegramBot telegramBot() {
        String botToken = applicationConfig.token();
        return new TelegramBot(botToken);
    }
}
