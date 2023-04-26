package org.example.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.dto.requests.AddLinkRequest;
import org.example.scrapper.dto.requests.RemoveLinkRequest;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.service.interfaces.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class ScrapperLinkController {
    private final LinkService linkService;

    @GetMapping()
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        ListLinksResponse response = linkService.listAll(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<LinkResponse> addLink(Long id, @RequestBody AddLinkRequest request) {
        LinkResponse response = linkService.add(id, request.url());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<LinkResponse> removeLink(Long id, @RequestBody RemoveLinkRequest request) {
        LinkResponse response = linkService.remove(id, request.url());
        return ResponseEntity.ok(response);
    }
}
