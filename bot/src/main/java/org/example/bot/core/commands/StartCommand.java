package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;

/**
 * Class for /start command
 */
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Register user";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        scrapperClient.registerChat(chatId);
        return new SendMessage(chatId, "Welcome! Type /help for a list of commands.");
    }
}
