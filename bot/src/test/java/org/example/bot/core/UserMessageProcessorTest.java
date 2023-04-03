package org.example.bot.core;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.client.ScrapperClient;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class UserMessageProcessorTest {

    @Test
    public void testProcessUnknownCommand() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        UserMessageProcessor userMessageProcessor = new UserMessageProcessor(scrapperClient);

        long chatId = 123L;
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(chatId);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);

        String command = "/someunknowncommand";
        Mockito.when(message.text()).thenReturn(command);

        SendMessage result = userMessageProcessor.process(update);
        SendMessage expected = new SendMessage(chatId, "Sorry, I don't understand you. Try /help to see list of commands");

        assertEquals(result.getParameters().get("chat_id"), expected.getParameters().get("chat_id"));
        assertEquals(result.getParameters().get("text"), expected.getParameters().get("text"));
    }
}
