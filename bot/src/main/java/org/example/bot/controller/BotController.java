package org.example.bot.controller;

import org.example.bot.dto.LinkUpdateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/updates")
public class BotController {

    @PostMapping
    public void processLinkUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        // TODO: Process link update

        //return ResponseEntity.ok(new LinkUpdateResponse("Update processed"));
    }
}
