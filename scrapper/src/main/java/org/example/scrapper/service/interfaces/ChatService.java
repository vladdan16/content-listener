package org.example.scrapper.service.interfaces;

public interface ChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);
}
