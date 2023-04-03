package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.core.UserMessageProcessor;

/**
 * Class for /help command
 */
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Display a list of commands";
    }

    @Override
    public SendMessage handle(Update update) {
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