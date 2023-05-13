package org.example.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.scrapper.service.interfaces.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class ScrapperChatController {
    private final ChatService chatService;

    /**
     * Method to register chat.
     * @param id telegram chat id
     * @return ResponseEntity
     */
    @PostMapping("/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        chatService.register(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Method to unregister chat.
     * @param id telegram chat id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
