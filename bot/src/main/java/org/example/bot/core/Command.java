package org.example.bot.core;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

/**
 * Interface that represents bot commands
 */
public interface Command {
    /**
     * Name of command
     * @return String value - command
     */
    String command();

    /**
     * Description of command
     * @return String value - description
     */
    String description();

    /**
     * Method to handle update from bot
     * @param update Update message
     * @return SendMessage instance for bot reply
     */
    SendMessage handle(Update update);

    /**
     * Default method to check if Command supports command
     * @param update Update to process
     * @return boolean value - if command supported
     */
    default boolean supports(Update update) {
        return update.message().text().equals(command());
    }

    /**
     * Default method to convert custom command to BotCommand
     * @return BotCommand instance
     */
    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
