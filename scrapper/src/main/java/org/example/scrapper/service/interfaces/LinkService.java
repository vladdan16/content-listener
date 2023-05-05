package org.example.scrapper.service.interfaces;

import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;

import java.net.URI;

public interface LinkService {
    /**
     * Method to add link.
     * @param tgChatId telegram chat id
     * @param url link
     * @return LinkResponse
     */
    LinkResponse add(long tgChatId, URI url);

    /**
     * Method to remove link.
     * @param tgChatId telegram chat id
     * @param url link
     * @return LinkResponse
     */
    LinkResponse remove(long tgChatId, URI url);

    /**
     * Method to get all links from database by chat id.
     * @param tgChatId telegram chat id
     * @return ListLinkResponse
     */
    ListLinksResponse listAll(long tgChatId);
}
