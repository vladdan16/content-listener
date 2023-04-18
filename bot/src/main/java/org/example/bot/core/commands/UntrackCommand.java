package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.RemoveLinkRequest;
import org.springframework.stereotype.Component;

/**
 * Class for /untrack command
 */
@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stop tracking a link. To untrack use command in the following format \"/untrack LINK_TO_UNTRACK\"";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String message = update.message().text();
        String link = message.substring(command().length()).trim();
        // TODO: implement untracking
        scrapperClient.removeLink(chatId, new RemoveLinkRequest(link));
        return new SendMessage(chatId, "Link was successfully removed");
    }

    @Override
    public boolean supports(Update update) {
        String message = update.message().text();
        String[] msg = message.split(" ");
        return msg.length == 2 && msg[0].equals(command());
    }
}