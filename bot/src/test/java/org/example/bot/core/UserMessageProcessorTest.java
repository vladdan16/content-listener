package org.example.bot.core;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.client.ScrapperClient;
import org.example.bot.core.commands.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UserMessageProcessorTest {
    @Mock
    Chat chat;
    @Mock
    Message message;
    @Mock
    Update update;
    @Mock
    ScrapperClient scrapperClient;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessUnknownCommand() {
        UserMessageProcessor userMessageProcessor = new UserMessageProcessor(
                Arrays.asList(
                        new StartCommand(scrapperClient),
                        new HelpCommand(scrapperClient),
                        new TrackCommand(scrapperClient),
                        new UntrackCommand(scrapperClient),
                        new ListCommand(scrapperClient)
                )
        );

        long chatId = 123L;
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        String command = "/someunknowncommand";
        when(message.text()).thenReturn(command);

        SendMessage result = userMessageProcessor.process(update);
        SendMessage expected = new SendMessage(chatId, "Sorry, I don't understand you. Try /help to see list of commands");

        assertEquals(result.getParameters().get("chat_id"), expected.getParameters().get("chat_id"));
        assertEquals(result.getParameters().get("text"), expected.getParameters().get("text"));
    }
}
