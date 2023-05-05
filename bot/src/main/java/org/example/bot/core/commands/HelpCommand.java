package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.core.UserMessageProcessor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Class for /help command.
 */
@Component
@RequiredArgsConstructor
public final class HelpCommand implements Command {
    /**
     * Client for Scrapper.
     */
    private final ScrapperClient scrapperClient;

    @Contract(pure = true) @Override
    public @NotNull String command() {
        return "/help";
    }

    @Contract(pure = true) @Override
    public @NotNull String description() {
        return "Display a list of commands";
    }

    @Contract("_ -> new") @Override
    public @NotNull SendMessage handle(final Update update) {
        StringBuilder builder = new StringBuilder();
        builder.append("Available commands:\n");
        for (Command c : UserMessageProcessor.commands()) {
            builder.append(c.command());
            builder.append(" - ");
            builder.append(c.description());
            builder.append("\n");
        }
        return new SendMessage(update.message().chat().id(), builder.toString());
    }
}
