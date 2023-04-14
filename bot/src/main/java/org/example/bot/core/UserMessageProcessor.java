package org.example.bot.core;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.core.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class to process messages from telegram bot
 */
@Component
public class UserMessageProcessor {

    public static List<? extends Command> commandList;

    @Autowired
    public UserMessageProcessor(List<? extends Command> commandList) {
        UserMessageProcessor.commandList = commandList;
    }

    public static List<? extends Command> commands() {
        return commandList;
    }

    /**
     * Process update from telegram bot to reply to user
     *
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
