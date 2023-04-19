package org.example.scrapper.service.interfaces;

import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;

import java.net.URI;

public interface LinkService {
    LinkResponse add(long tgChatId, URI url);
    LinkResponse remove(long tgChatId, URI url);
    ListLinksResponse listAll(long tgChatId);
}