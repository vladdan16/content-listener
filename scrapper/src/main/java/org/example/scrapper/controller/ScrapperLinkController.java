package org.example.scrapper.controller;

import org.example.scrapper.dto.requests.AddLinkRequest;
import org.example.scrapper.dto.responses.LinkResponse;
import org.example.scrapper.dto.responses.ListLinksResponse;
import org.example.scrapper.dto.requests.RemoveLinkRequest;
import org.example.scrapper.temp.Database;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/links")
public class ScrapperLinkController {
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        // TODO: get links
        // Temporary solution
        List<String> list = Database.getInstance().getAllLinksFromUser(id);
        List<LinkResponse> ans = new ArrayList<>();
        for (String s : list) {
            ans.add(new LinkResponse(id, s));
        }
        ListLinksResponse links = new ListLinksResponse(ans, list.size());
        return ResponseEntity.ok(links);
    }

    @PostMapping( produces = {"application/json"})
    public ResponseEntity<LinkResponse> addLink(Long id, @RequestBody AddLinkRequest request) {
        // TODO: implement add link
        // Temporary solution
        Database.getInstance().addLinkToUser(id, request.url());
        LinkResponse link = new LinkResponse(id, "Link successfully added");
        return ResponseEntity.ok(link);
    }

    @DeleteMapping(produces = {"appllication/json"})
    public ResponseEntity<LinkResponse> removeLink(Long id, @RequestBody RemoveLinkRequest request) {
        // TODO: implement removing links
        // Temporary solution
        Database.getInstance().removeLinkFromUser(id, request.url());
        LinkResponse link = new LinkResponse(id, "Link successfully removed");
        return ResponseEntity.ok(link);
    }
}
