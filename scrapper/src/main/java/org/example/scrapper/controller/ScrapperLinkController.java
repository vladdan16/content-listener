package org.example.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.requests.AddLinkRequest;
import org.example.scrapper.dto.requests.RemoveLinkRequest;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.service.interfaces.LinkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public final class ScrapperLinkController {
    /**
     * Link service.
     */
    private final LinkService linkService;

    /**
     * Method to get Links by id.
     * @param id telegram chat id
     * @return ResponseEntity
     */
    @GetMapping()
    public @NotNull ResponseEntity<ListLinksResponse> getLinks(final Long id) {
        ListLinksResponse response = linkService.listAll(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Method to add link.
     * @param id telegram chat id.
     * @param request AddLinkRequest
     * @return ResponseEntity
     */
    @PostMapping()
    public @NotNull ResponseEntity<LinkResponse> addLink(final Long id, @RequestBody @NotNull final AddLinkRequest request) {
        LinkResponse response = linkService.add(id, request.url());
        return ResponseEntity.ok(response);
    }

    /**
     * Method to remove link.
     * @param id telegram chat id
     * @param request RemoveLinkRequest
     * @return ResponseEntity
     */
    @DeleteMapping()
    public @NotNull ResponseEntity<LinkResponse> removeLink(final Long id, @RequestBody @NotNull final RemoveLinkRequest request) {
        LinkResponse response = linkService.remove(id, request.url());
        return ResponseEntity.ok(response);
    }
}
