package org.example.bot.core;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bot.core.commands.Command;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

/**
 * Class-wrapper for bot.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyBot implements AutoCloseable, UpdatesListener {
    private final TelegramBot telegramBot;
    private final UserMessageProcessor userMessageProcessor;
    private static final String ERROR = "Error while sending message: ";

    /**
     * void method to start our bot.
     */
    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(this);
        setCommands();
    }

    /**
     * Method to process updates from telegram.
     * @param list list of Updates
     * @return int code
     */
    @Override
    public int process(List<Update> list) {
        //return UpdatesListener.CONFIRMED_UPDATES_ALL;
        list.forEach(update -> {
            SendMessage message = userMessageProcessor.process(update);
            if (message != null) {
                BaseResponse response = telegramBot.execute(message);
                if (!response.isOk()) {
                    log.error(ERROR + response.description());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * void method to close bot.
     */
    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
    }

    /**
     * Method to process updates from scrapper and send to user.
     * @param url Link that is tracked
     * @param description Description for user
     * @param tgChatIds List of chats where to send update
     */
    public void processUpdate(String url, String description, List<Long> tgChatIds) {
        String text = description + "\n" + url;
        tgChatIds.forEach(id -> {
            SendMessage message = new SendMessage(id, text);
            BaseResponse response = telegramBot.execute(message);
            if (!response.isOk()) {
                log.error(ERROR + response.description());
            }
        });
    }

    /**
     * void method to set commands dor telegram bot (Bonus task).
     */
    private void setCommands() {
        ArrayList<BotCommand> botCommands = new ArrayList<>();
        for (Command c : UserMessageProcessor.commands()) {
            botCommands.add(c.toApiCommand());
        }
        SetMyCommands setMyCommands = new SetMyCommands(botCommands.toArray(new BotCommand[0]));
        telegramBot.execute(setMyCommands);
    }
}
