package org.example.scrapper.domain.interfaces;

import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;

import java.util.List;

/**
 * Interface that represents Link repository
 */
public interface LinkDao {
    /**
     * Method that adds Link to repo
     * @param link String value of link
     * @param chatId id of telegram chat
     * @return Link that was added
     */
    LinkDto add(String link, Long chatId);

    /**
     * Method that removes links from repo
     * @param link String value of link
     * @param chatId id of telegram chat
     * @return Link that was removed
     */
    LinkDto remove(String link, Long chatId);

    /**
     * Method that finds all links in repo
     * @return List of Links
     */
    List<LinkDto> findAll();

    /**
     * Method that updates link in repo
     * @param link String value of link
     */
    void update(String  link);

    /**
     * Method that finds link in repo by String value
     * @param link String value of link
     * @return Link object
     */
    LinkDto findLink(String link);

    /**
     * Method that finds all Chats that subscribed to specified link
     * @param link String value of link
     * @return List of Chat objects
     */
    List<ChatDto> findSubscribers(String link);

    /**
     * Method that finds all old links, for example those which have not been checked for 1 minute
     * @return List of Links
     */
    List<LinkDto> findAllOldLinks();
}
