package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.JdbcLinkDao;
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

    @Override
    public LinkResponse add(long tgChatId, @NotNull URI url) {
        // TODO: fix adding to repo
        LinkDto link = new LinkDto();
        link.setOwnerId(tgChatId);
        link.setLink(url.toString());
        linkRepository.add(link);
        return new LinkResponse(link.getId(), url.toString());
    }

    @Override
    public LinkResponse remove(long tgChatId, @NotNull URI url) {
        linkRepository.remove(url.toString());
        return new LinkResponse(1L, url.toString());
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        List<LinkDto> list = linkRepository.findAll();
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (LinkDto e : list) {
            linkResponses.add(new LinkResponse(e.getId(), e.getLink()));
        }
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }
}
