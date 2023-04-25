package org.example.scrapper.domain.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class JpaChatDao {
    private final ChatRepository chatRepository;

    @Transactional
    public Chat add(Long id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Chat chat = new Chat(id, timestamp);
        chatRepository.save(chat);
        return chat;
    }

    @Transactional
    public void remove(long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        //find and remove links from chat
        Set<Link> links = chat.getLinks();
        links.forEach(link -> link.getChats().remove(chat));
        chat.getLinks().clear();

        chatRepository.delete(chat);
    }

    @Transactional
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Transactional
    public List<Link> findAllLinksById(long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        return new ArrayList<>(chat.getLinks());
    }
}
