package org.example.scrapper.controller;

import org.example.scrapper.dto.AddLinkRequest;
import org.example.scrapper.dto.LinkResponse;
import org.example.scrapper.dto.ListLinksResponse;
import org.example.scrapper.dto.RemoveLinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/links")
public class ScrapperLinkController {
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        // TODO: get links
        ListLinksResponse links = new ListLinksResponse(null ,0);
        return ResponseEntity.ok(links);
    }

    @PostMapping( produces = {"application/json"})
    public ResponseEntity<LinkResponse> addLink(Long id, @RequestBody AddLinkRequest request) {
        // TODO: implement add link
        LinkResponse link = new LinkResponse(id, "Link successfully added");
        return ResponseEntity.ok(link);
    }

    @DeleteMapping(produces = {"apllication/json"})
    public ResponseEntity<LinkResponse> removeLink(Long id, @RequestBody RemoveLinkRequest request) {
        // TODO: implement removing links
        LinkResponse link = new LinkResponse(id, "Link successfully removed");
        return ResponseEntity.ok(link);
    }
}
