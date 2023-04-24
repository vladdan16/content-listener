package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.jdbc.JdbcChatDao;
import org.example.scrapper.domain.jdbc.JdbcLinkDao;
import org.example.scrapper.domain.dto.LinkDto;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.service.interfaces.LinkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
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
        LinkDto link = linkRepository.add(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink());
    }

    @Override
    public LinkResponse remove(long tgChatId, @NotNull URI url) {
        LinkDto link = linkRepository.remove(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink());
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        List<LinkDto> list = chatRepository.findAllLinksById(tgChatId);
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (LinkDto e : list) {
            linkResponses.add(new LinkResponse(e.getId(), e.getLink()));
        }
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }
}
