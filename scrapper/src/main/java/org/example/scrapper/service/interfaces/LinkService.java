package org.example.scrapper.service.interfaces;

import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListChatResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;

import java.net.URI;
import java.sql.Timestamp;

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
     * @return ListLinksResponse
     */
    ListLinksResponse listAll(long tgChatId);

    /**
     * Method to update link in database.
     * @param url String url
     * @param updatedAt Timestamp
     */
    void update(String url, Timestamp updatedAt);

    /**
     * Method to get all subscribers from database by link.
     * @param url String url
     * @return ListChatResponse all subscribers
     */
    ListChatResponse findSubscribers(String url);

    /**
     * Method to get all old links from database by interval.
     * @param interval String value of interval in Postgres
     * @return ListLinksResponse List of links
     */
    ListLinksResponse findAllOldLinks(String interval);
}
