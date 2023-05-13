package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jdbc.JdbcChatDao;
import org.example.scrapper.service.interfaces.ChatService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatDao chatRepository;

    /**
     * Method to register chat by id.
     * @param tgChatId telegram chat id
     */
    @Override
    public void register(long tgChatId) {
        chatRepository.add(tgChatId);
    }

    /**
     * Method to remove chat id.
     * @param tgChatId telegram chat id
     */
    @Override
    public void unregister(long tgChatId) {
        chatRepository.remove(tgChatId);
    }
}
