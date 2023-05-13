package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for /list command.
 */
@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    /**
     * Scrapper client.
     */
    private final ScrapperClient scrapperClient;

    /**
     * Name of command.
     * @return String value - command
     */
    @Override
    public String command() {
        return "/list";
    }

    /**
     * Description of command.
     * @return String value - description
     */
    @Override
    public String description() {
        return "Command to list all tracking links";
    }

    /**
     * Method to handle update from bot.
     * @param update Update message
     * @return SendMessage instance for bot reply
     */
    @Override
    public SendMessage handle(@NotNull final Update update) {
        long chatId = update.message().chat().id();
        ListLinksResponse response = scrapperClient.getLINKS(chatId);
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
