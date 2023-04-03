package org.example.bot.core;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.client.ScrapperClient;
import org.example.bot.core.commands.*;

import java.util.Arrays;
import java.util.List;

/**
 * Class to process messages from telegram bot
 */
public class UserMessageProcessor {

    private static ScrapperClient scrapperClient;

    public UserMessageProcessor(ScrapperClient scrapperClient) {
        UserMessageProcessor.scrapperClient = scrapperClient;
    }

    /**
     * List of all possible commands
     * @return List<Command>
     */
    public static List<? extends Command> commands() {
        return Arrays.asList(
                new StartCommand(scrapperClient),
                new HelpCommand(scrapperClient),
                new TrackCommand(scrapperClient),
                new UntrackCommand(scrapperClient),
                new ListCommand(scrapperClient)
        );
    }

    /**
     * Process update from telegram bot to reply to user
     * @param update Update from bot
     * @return SendMessage instance for our bot
     */
    SendMessage process(Update update) {
        if (update.message() == null) { // do nothing
            return null;
        }
        long chatId = update.message().chat().id();

        String command = update.message().text().split(" ")[0];

        if (command.startsWith("/")) {
            for (Command c : commands()) {
                if (c.supports(update)) {
                    return c.handle(update);
                }
            }
        }
        return new SendMessage(chatId, "Sorry, I don't understand you. Try /help to see list of commands");
    }
}
