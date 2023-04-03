package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.AddLinkRequest;

/**
 * Class for /track command
 */
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start tracking a link. To track use command in the following format \"/track LINK_TO_TRACK\"";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String message = update.message().text();
        String link = message.substring(command().length()).trim();
        scrapperClient.addLink(chatId, new AddLinkRequest(link));
        return new SendMessage(chatId, "Started tracking " + link);
    }

    @Override
    public boolean supports(Update update) {
        String message = update.message().text();
        String[] msg = message.split(" ");
        return msg.length == 2 && msg[0].equals(command());
    }
}