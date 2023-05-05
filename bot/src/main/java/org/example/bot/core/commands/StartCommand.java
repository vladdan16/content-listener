package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Class for /start command.
 */
@Component
@RequiredArgsConstructor
public final class StartCommand implements Command {
    /**
     * Scrapper client.
     */
    private final ScrapperClient scrapperClient;

    @Contract(pure = true) @Override
    public @NotNull String command() {
        return "/start";
    }

    @Contract(pure = true) @Override
    public @NotNull String description() {
        return "Register user";
    }

    @Contract("_ -> new") @Override
    public @NotNull SendMessage handle(@NotNull final Update update) {
        long chatId = update.message().chat().id();
        scrapperClient.registerChat(chatId);
        return new SendMessage(chatId, "Welcome! Type /help for a list of commands.");
    }
}
