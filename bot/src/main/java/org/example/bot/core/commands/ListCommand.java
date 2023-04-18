package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for /list command
 */
@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Command to list all tracking links";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        ListLinksResponse response = scrapperClient.getLinks(chatId);
        List<String> links = new ArrayList<>();
        for (LinkResponse r : response.links()) {
            links.add(r.url().toString());
        }
        if (links.isEmpty()) {
            return new SendMessage(chatId, "There are no links to track");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Here is the list of all tracked links\n");
        for (String link : links) {
            builder.append(link).append("\n");
        }
        return new SendMessage(chatId, builder.toString());
    }
}