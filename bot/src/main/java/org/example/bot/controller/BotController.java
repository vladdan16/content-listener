package org.example.bot.controller;

import org.example.bot.controller.dto.LinkUpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class BotController {

    @PostMapping("/updates")
    public void processLinkUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        // TODO: Process link update

        //return ResponseEntity.ok(new LinkUpdateResponse("Update processed"));
    }
}
