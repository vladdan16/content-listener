package org.example.scrapper.service.interfaces;

public interface TgChatService {
    /**
     * Method to register chat by id.
     * @param tgChatId telegram chat id
     */
    void register(long tgChatId);

    /**
     * Method to remove chat id.
     * @param tgChatId telegram chat id
     */
    void unregister(long tgChatId);
}
