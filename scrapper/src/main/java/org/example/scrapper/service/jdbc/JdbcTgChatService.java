package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jdbc.JdbcChatDao;
import org.example.scrapper.service.interfaces.TgChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatDao chatRepository;
    @Override
    public void register(long tgChatId) {
        chatRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.remove(tgChatId);
    }
}
