package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jdbc.Chat;
import org.example.scrapper.domain.jdbc.JdbcChatDao;
import org.example.scrapper.domain.jdbc.JdbcLinkDao;
import org.example.scrapper.domain.jdbc.Link;
import org.example.scrapper.dto.responses.ChatResponse;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListChatResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.service.interfaces.LinkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao linkRepository;
    private final JdbcChatDao chatRepository;

    @Override
    public LinkResponse add(long tgChatId, @NotNull URI url) {
        Link link = linkRepository.add(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink(), link.getUpdatedAt());
    }

    @Override
    public LinkResponse remove(long tgChatId, @NotNull URI url) {
        Link link = linkRepository.remove(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink(), link.getUpdatedAt());
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        List<Link> list = chatRepository.findAllLinksById(tgChatId);
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (Link e : list) {
            linkResponses.add(new LinkResponse(e.getId(), e.getLink(), e.getUpdatedAt()));
        }
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }

    @Override
    public void update(String url, Timestamp updatedAt) {
        linkRepository.update(url, updatedAt);
    }

    @Override
    public ListChatResponse findSubscribers(String url) {
        List<Chat> chats = linkRepository.findSubscribers(url);
        List<ChatResponse> responses = new ArrayList<>();
        for (Chat e : chats) {
            responses.add(new ChatResponse(e.getId()));
        }
        return new ListChatResponse(responses, responses.size());
    }

    @Override
    public ListLinksResponse findAllOldLinks(String interval) {
        List<Link> list = linkRepository.findAllOldLinks(interval);
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (Link e : list) {
            linkResponses.add(new LinkResponse(e.getId(), e.getLink(), e.getUpdatedAt()));
        }
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }
}
