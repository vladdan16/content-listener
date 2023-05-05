package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.JdbcChatDao;
import org.example.scrapper.service.interfaces.TgChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    /**
     * JdbcChatDao repository.
     */
    private final JdbcChatDao chatRepository;

    /**
     * Method to register chat by id.
     * @param tgChatId telegram chat id
     */
    @Override
    public void register(final long tgChatId) {
        chatRepository.add(tgChatId);
    }

    /**
     * Method to remove chat id.
     * @param tgChatId telegram chat id
     */
    @Override
    public void unregister(final long tgChatId) {
        chatRepository.remove(tgChatId);
    }
}
