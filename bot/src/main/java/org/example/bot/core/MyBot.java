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
import org.example.bot.core.commands.Command;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class-wrapper for bot
 */
@Component
@RequiredArgsConstructor
public class MyBot implements AutoCloseable, UpdatesListener {
    private final TelegramBot telegramBot;
    private final UserMessageProcessor userMessageProcessor;

    /**
     * void method to start our bot
     */
    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(this);
        setCommands();
    }

    @Override
    public int process(List<Update> list) {
        //return UpdatesListener.CONFIRMED_UPDATES_NONE;
        list.forEach(update -> {
            SendMessage message = userMessageProcessor.process(update);
            if (message != null) {
                BaseResponse response = telegramBot.execute(message);
                if (!response.isOk()) {
                    System.out.println("Error while sending message: "+  response.description());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * void method to close bot
     */
    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
    }

    /**
     * void method to set commands dor telegram bot (Bonus task)
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
