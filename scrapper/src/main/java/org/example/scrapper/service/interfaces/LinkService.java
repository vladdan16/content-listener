package org.example.scrapper.service.interfaces;

import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListChatResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;

import java.net.URI;
import java.sql.Timestamp;

public interface LinkService {
    LinkResponse add(long tgChatId, URI url);
    LinkResponse remove(long tgChatId, URI url);
    ListLinksResponse listAll(long tgChatId);
    void update(String url, Timestamp updatedAt);
    ListChatResponse findSubscribers(String url);
    ListLinksResponse findAllOldLinks(String interval);
}