package org.example.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.service.interfaces.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class ScrapperChatController {
    private final ChatService chatService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        chatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
