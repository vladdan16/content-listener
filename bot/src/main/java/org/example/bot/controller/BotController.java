package org.example.bot.controller;

import org.example.bot.dto.LinkUpdateErrorResponse;
import org.example.bot.dto.LinkUpdateOKResponse;
import org.example.bot.dto.LinkUpdateRequest;
import org.example.bot.dto.LinkUpdateResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RequestMapping("/updates")
public class BotController {

    @PostMapping
    public ResponseEntity<LinkUpdateResponse> processLinkUpdate(LinkUpdateRequest linkUpdateRequest) {
        // TODO: Process link update

        return ResponseEntity.ok(new LinkUpdateOKResponse("Update processed"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LinkUpdateResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        LinkUpdateResponse response = new LinkUpdateErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LinkUpdateResponse> handleException(Exception e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        LinkUpdateResponse response = new LinkUpdateErrorResponse("Internal server error", "500", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
