package org.example.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
public class ScrapperChatController {

    @PostMapping("/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        // TODO: register chat
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        // TODO: delete chat
        return ResponseEntity.ok().build();
    }
}
