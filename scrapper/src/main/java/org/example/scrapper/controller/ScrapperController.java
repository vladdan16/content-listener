package org.example.scrapper.controller;

import org.example.scrapper.dto.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RequestMapping("/tg-scrapper")
public class ScrapperController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable int id) {
        // TODO: register chat
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable int id) {
        // TODO: delete chat
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(int id) {
        // TODO: get links
        ListLinksResponse links = new ListLinksResponse(null ,0);
        return ResponseEntity.ok(links);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(int id, @RequestBody AddLinkRequest request) {
        // TODO: implement add link
        LinkResponse link = new LinkResponse(id, "Link added successfully");
        return ResponseEntity.ok(link);
    }

    public ResponseEntity<Void> removeLink(int id, @RequestBody RemoveLinkRequest request) {
        // TODO: implement removing links
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiErrorResponse response = new ApiErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Chat not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Link not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Internal server error", "500", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
