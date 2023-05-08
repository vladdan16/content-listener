package org.example.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jpa.*;
import org.example.scrapper.dto.responses.ChatResponse;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListChatResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.service.interfaces.LinkService;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;

    @Override
    public LinkResponse add(long tgChatId, URI url) {
        Link link = linkRepository.findLink(url.toString());
        if (link == null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            link = new Link(null, url.toString(), timestamp, null, null);
        }
        Chat chat = chatRepository.findById(tgChatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        link.getChats().add(chat);
        chat.getLinks().add(link);
        link = linkRepository.save(link);
        return new LinkResponse(link.getId(), link.getLink(), link.getUpdatedAt());
    }

    @Override
    public LinkResponse remove(long tgChatId, URI url) {
        Chat chat = chatRepository.findById(tgChatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        Link link = linkRepository.findLink(url.toString());
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
        return new LinkResponse(link.getId(), link.getLink(), link.getUpdatedAt());
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        List<Link> links = linkRepository.findAll();
        List<LinkResponse> responses = new ArrayList<>();
        for (Link e : links) {
            responses.add(new LinkResponse(e.getId(), e.getLink(), e.getUpdatedAt()));
        }
        return new ListLinksResponse(responses, responses.size());
    }

    @Override
    public void update(String url, Timestamp updatedAt) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Link link = linkRepository.findLink(url);
        link.setTimeChecked(timestamp);
        link.setUpdatedAt(updatedAt);
        linkRepository.save(link);
    }

    @Override
    public ListChatResponse findSubscribers(String url) {
        Link link = linkRepository.findLink(url);
        if (link == null) {
            throw new EntityNotFoundException("Link not found");
        }
        List<Chat> chats =  new ArrayList<>(link.getChats());

        List<ChatResponse> responses = new ArrayList<>();
        for (Chat e : chats) {
            responses.add(new ChatResponse(e.getId()));
        }
        return new ListChatResponse(responses, responses.size());
    }

    @Override
    public ListLinksResponse findAllOldLinks(String interval) {
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
        List<Link> links = linkRepository.findAllByTimeCheckedBefore(timestamp);

        List<LinkResponse> responses = new ArrayList<>();
        for (Link e : links) {
            responses.add(new LinkResponse(e.getId(), e.getLink(), e.getUpdatedAt()));
        }
        return new ListLinksResponse(responses, responses.size());
    }
}
