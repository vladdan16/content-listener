package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;
import org.example.bot.core.commands.ListCommand;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListCommandTest {

    @Test
    public void testHandleWithEmptyLinks() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        ListCommand listCommand = new ListCommand(scrapperClient);
        long chatId = 123L;
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(chatId);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(scrapperClient.getLinks(chatId)).thenReturn(new ListLinksResponse(Collections.emptyList(), 0));

        SendMessage result = listCommand.handle(update);
        SendMessage expected = new SendMessage(chatId, "There are no links to track");

        assertEquals(result.getParameters().get("chat_id"), expected.getParameters().get("chat_id"));
        assertEquals(result.getParameters().get("text"), expected.getParameters().get("text"));
    }

    @Test
    public void testHandleWithLinks() throws URISyntaxException {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        ListCommand listCommand = new ListCommand(scrapperClient);
        long chatId = 123L;
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(chatId);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.message()). thenReturn(message);
        List<LinkResponse> links = new ArrayList<>();
        links.add(new LinkResponse(1L, new URI("https://github.com/vladdan16/content-listener")));
        links.add(new LinkResponse(2L, new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c")));
        Mockito.when(scrapperClient.getLinks(chatId)).thenReturn(new ListLinksResponse(links, links.size()));

        SendMessage result = listCommand.handle(update);
        SendMessage expected = new SendMessage(chatId, """
                Here is the list of all tracked links
                https://github.com/vladdan16/content-listener
                https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c
                """);

        assertEquals(result.getParameters().get("chat_id"), expected.getParameters().get("chat_id"));
        assertEquals(result.getParameters().get("text"), expected.getParameters().get("text"));
    }
}
