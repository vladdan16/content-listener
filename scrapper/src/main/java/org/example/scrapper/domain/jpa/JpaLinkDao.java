package org.example.scrapper.domain.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaLinkDao {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;

    @Transactional
    public Link add(String url, Long chatId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        Link link = new Link(null, url, timestamp, null, null);
        link.getChats().add(chat);
        chat.getLinks().add(link);
        return linkRepository.save(link);
    }

    @Transactional
    public Link remove(String url, long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        Link link = linkRepository.findLink(url);
        if (link == null) {
            throw new EntityNotFoundException("There is no such link");
        }

        link.getChats().remove(chat);
        chat.getLinks().remove(link);

        if (link.getChats().isEmpty()) {
            linkRepository.delete(link);
        } else {
            linkRepository.save(link);
        }
        return link;
    }

    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    public List<Chat> findSubscribers(String url) {
        Link link = linkRepository.findLink(url);

        if (link == null) {
            throw new EntityNotFoundException("Link not found");
        }

        return new ArrayList<>(link.getChats());
    }

    /**
     * Method to find links that was not checked for some intreval
     * @param interval String value in the following format "X days/hours/minutes/seconds"
     * @return List of Links
     */
    public List<Link> findAllOldLinks(String interval) {
        String[] parts = interval.trim().split(" ");
        int amount = Integer.parseInt(parts[0]);
        String unit = parts[1];

        Timestamp timestamp = switch (unit) {
            case "days" -> new Timestamp(System.currentTimeMillis() - amount * 24 * 60 * 60 * 1000L);
            case "hours" -> new Timestamp(System.currentTimeMillis() - amount * 60 * 60 * 1000L);
            case "minutes" -> new Timestamp(System.currentTimeMillis() - amount * 60 * 1000L);
            case "seconds" -> new Timestamp(System.currentTimeMillis() - amount * 1000L);
            default -> throw new IllegalArgumentException("Invalid interval format");
        };
        return linkRepository.findAllByTimeCheckedBefore(timestamp);
    }
}

















