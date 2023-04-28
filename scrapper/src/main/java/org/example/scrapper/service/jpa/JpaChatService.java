package org.example.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jpa.Chat;
import org.example.scrapper.domain.jpa.ChatRepository;
import org.example.scrapper.domain.jpa.Link;
import org.example.scrapper.service.interfaces.ChatService;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Set;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void register(long tgChatId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Chat chat = new Chat(tgChatId, timestamp);
        chatRepository.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        //find and remove links from chat
        Set<Link> links = chat.getLinks();
        links.forEach(link -> link.getChats().remove(chat));
        chat.getLinks().clear();

        chatRepository.delete(chat);
    }
}
