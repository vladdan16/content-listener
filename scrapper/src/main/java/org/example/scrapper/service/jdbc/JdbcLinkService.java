package org.example.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.domain.JdbcChatDao;
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
    /**
     * JdbcLinkDao repository.
     */
    private final JdbcLinkDao linkRepository;
    /**
     * JdbcChatDao repository.
     */
    private final JdbcChatDao chatRepository;

    /**
     * Method to add link.
     * @param tgChatId telegram chat id
     * @param url link
     * @return LinkResponse
     */
    @Override
    public LinkResponse add(final long tgChatId, @NotNull final URI url) {
        LinkDto link = linkRepository.add(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink());
    }

    /**
     * Method to remove link.
     * @param tgChatId telegram chat id
     * @param url link
     * @return LinkResponse
     */
    @Override
    public LinkResponse remove(final long tgChatId, @NotNull final URI url) {
        LinkDto link = linkRepository.remove(url.toString(), tgChatId);
        return new LinkResponse(link.getId(), link.getLink());
    }

    /**
     * Method to get all links from database by chat id.
     * @param tgChatId telegram chat id
     * @return ListLinkResponse
     */
    @Override
    public ListLinksResponse listAll(final long tgChatId) {
        List<LinkDto> list = chatRepository.findAllLinksById(tgChatId);
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (LinkDto e : list) {
            linkResponses.add(new LinkResponse(e.getId(), e.getLink()));
        }
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }
}
