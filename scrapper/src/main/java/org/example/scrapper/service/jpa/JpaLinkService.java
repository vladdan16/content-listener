package org.example.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jpa.Chat;
import org.example.scrapper.domain.jpa.JpaLinkDao;
import org.example.scrapper.domain.jpa.Link;
import org.example.scrapper.dto.responses.ChatResponse;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListChatResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.service.interfaces.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkDao jpaLinkDao;

    @Override
    public LinkResponse add(long tgChatId, URI url) {
        Link link = jpaLinkDao.add(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink(), link.getUpdatedAt());
    }

    @Override
    public LinkResponse remove(long tgChatId, URI url) {
        Link link = jpaLinkDao.remove(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink(), link.getUpdatedAt());
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        List<Link> links = jpaLinkDao.findAll();
        List<LinkResponse> responses = new ArrayList<>();
        for (Link e : links) {
            responses.add(new LinkResponse(e.getId(), e.getLink(), e.getUpdatedAt()));
        }
        return new ListLinksResponse(responses, responses.size());
    }

    @Override
    public void update(String url, Timestamp updatedAt) {
        jpaLinkDao.update(url, updatedAt);
    }

    @Override
    public ListChatResponse findSubscribers(String url) {
        List<Chat> chats = jpaLinkDao.findSubscribers(url);
        List<ChatResponse> responses = new ArrayList<>();
        for (Chat e : chats) {
            responses.add(new ChatResponse(e.getId()));
        }
        return new ListChatResponse(responses, responses.size());
    }

    @Override
    public ListLinksResponse findAllOldLinks(String interval) {
        List<Link> links = jpaLinkDao.findAllOldLinks(interval);
        List<LinkResponse> responses = new ArrayList<>();
        for (Link e : links) {
            responses.add(new LinkResponse(e.getId(), e.getLink(), e.getUpdatedAt()));
        }
        return new ListLinksResponse(responses, responses.size());
    }
}
