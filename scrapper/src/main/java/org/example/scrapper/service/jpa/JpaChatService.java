package org.example.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jpa.JpaChatDao;
import org.example.scrapper.service.interfaces.ChatService;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatDao jpaChatDao;

    @Override
    public void register(long tgChatId) {
        jpaChatDao.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        jpaChatDao.remove(tgChatId);
    }
}
