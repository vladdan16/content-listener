package org.example.scrapper.service.interfaces;

public interface TgChatService {
    void register(long tgChatId);
    void unregister(long tgChatId);
}