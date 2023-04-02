package org.example.scrapper.controller;

import org.example.scrapper.temp.Database;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
public class ScrapperChatController {

    @PostMapping("/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        // TODO: register chat
        // temporary solution
        Database.getInstance().registerUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        // TODO: delete chat
        // temporary solution
        Database.getInstance().deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
