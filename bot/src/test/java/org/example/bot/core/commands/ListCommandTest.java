package org.example.bot.core.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ListCommandTest {
    @InjectMocks
    ListCommand listCommand;
    @Mock
    ScrapperClient scrapperClient;
    @Mock
    Chat chat;
    @Mock
    Message message;
    @Mock
    Update update;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleWithEmptyLinks() {
        long chatId = 123L;
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        when(scrapperClient.getLinks(chatId)).thenReturn(new ListLinksResponse(Collections.emptyList(), 0));

        SendMessage result = listCommand.handle(update);
        SendMessage expected = new SendMessage(chatId, "There are no links to track");

        assertEquals(result.getParameters().get("chat_id"), expected.getParameters().get("chat_id"));
        assertEquals(result.getParameters().get("text"), expected.getParameters().get("text"));
    }

    @Test
    public void testHandleWithLinks() throws URISyntaxException {
        long chatId = 123L;
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        List<LinkResponse> links = new ArrayList<>();
        links.add(new LinkResponse(1L, new URI("https://github.com/vladdan16/content-listener")));
        links.add(new LinkResponse(2L, new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c")));
        when(scrapperClient.getLinks(chatId)).thenReturn(new ListLinksResponse(links, links.size()));

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
