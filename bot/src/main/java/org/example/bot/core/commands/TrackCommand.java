package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.AddLinkRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Class for /track command.
 */
@Component
@RequiredArgsConstructor
public final class TrackCommand implements Command {
    /**
     * Scrapper client.
     */
    private final ScrapperClient scrapperClient;

    @Contract(pure = true) @Override
    public @NotNull String command() {
        return "/track";
    }

    @Contract(pure = true) @Override
    public @NotNull String description() {
        return "Start tracking a link. To track use command in the following format \"/track LINK_TO_TRACK\"";
    }

    @Contract("_ -> new") @Override
    public @NotNull SendMessage handle(@NotNull final Update update) {
        long chatId = update.message().chat().id();
        String message = update.message().text();
        String link = message.substring(command().length()).trim();
        scrapperClient.addLink(chatId, new AddLinkRequest(link));
        return new SendMessage(chatId, "Started tracking " + link);
    }

    @Override
    public boolean supports(@NotNull final Update update) {
        String message = update.message().text();
        String[] msg = message.split(" ");
        return msg.length == 2 && msg[0].equals(command());
    }
}
