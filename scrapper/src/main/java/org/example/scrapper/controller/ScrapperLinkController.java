package org.example.scrapper.controller;

import org.example.scrapper.dto.AddLinkRequest;
import org.example.scrapper.dto.LinkResponse;
import org.example.scrapper.dto.ListLinksResponse;
import org.example.scrapper.dto.RemoveLinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RequestMapping("/tg-scrapper")
public class ScrapperLinkController {
    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        // TODO: get links
        ListLinksResponse links = new ListLinksResponse(null ,0);
        return ResponseEntity.ok(links);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(Long id, @RequestBody AddLinkRequest request) {
        // TODO: implement add link
        LinkResponse link = new LinkResponse(id, "Link added successfully");
        return ResponseEntity.ok(link);
    }

    @DeleteMapping("/links")
    public ResponseEntity<Void> removeLink(Long id, @RequestBody RemoveLinkRequest request) {
        // TODO: implement removing links
        return ResponseEntity.ok().build();
    }
}
