package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.RemoveLinkRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Class for /untrack command.
 */
@Component
@RequiredArgsConstructor
public final class UntrackCommand implements Command {
    /**
     * Scrapper client.
     */
    private final ScrapperClient scrapperClient;

    @Contract(pure = true) @Override
    public @NotNull String command() {
        return "/untrack";
    }

    @Contract(pure = true) @Override
    public @NotNull String description() {
        return "Stop tracking a link. To untrack use command in the following format \"/untrack LINK_TO_UNTRACK\"";
    }

    @Contract("_ -> new") @Override
    public @NotNull SendMessage handle(@NotNull final Update update) {
        long chatId = update.message().chat().id();
        String message = update.message().text();
        String link = message.substring(command().length()).trim();
        scrapperClient.removeLink(chatId, new RemoveLinkRequest(link));
        return new SendMessage(chatId, "Link was successfully removed");
    }

    @Override
    public boolean supports(@NotNull final Update update) {
        String message = update.message().text();
        String[] msg = message.split(" ");
        return msg.length == 2 && msg[0].equals(command());
    }
}
