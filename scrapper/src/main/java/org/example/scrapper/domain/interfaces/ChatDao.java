package org.example.scrapper.domain.interfaces;

import org.example.scrapper.domain.dto.ChatDto;
import org.example.scrapper.domain.dto.LinkDto;

import java.util.List;

/**
 * Interface that represents Chat repository
 */
public interface ChatDao {
    /**
     * Method that adds chat to repo
     * @param chatId id of telegram chat
     * @return ChatDto object
     */
    ChatDto add(Long chatId);

    /**
     * Method that removes chat from repo
     * @param chatId id of telegram chat
     */
    void remove(long chatId);

    /**
     * Method that finds all chats in repository
     * @return List of Chats
     */
    List<ChatDto> findAll();

    /**
     * Method that finds all links on which user subscribed
     * @param chatId id of telegram chat
     * @return List of Links
     */
    List<LinkDto> findAllLinksById(long chatId);
}
