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
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        // TODO: register chat
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        // TODO: delete chat
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        // TODO: get links
        ListLinksResponse links = new ListLinksResponse(null ,0);
        return ResponseEntity.ok(links);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(Long id, @RequestBody AddLinkRequest request) {
        // TODO: implement add link
        LinkResponse link = new LinkResponse(id, "Link added successfully");
        return ResponseEntity.ok(link);
    }

    @DeleteMapping("/links")
    public ResponseEntity<Void> removeLink(Long id, @RequestBody RemoveLinkRequest request) {
        // TODO: implement removing links
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new ApiErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
    }

    @ExceptionHandler(value = {InstanceNotFoundException.class, AttributeNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleChatNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        return new ApiErrorResponse("Not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        return new ApiErrorResponse("Internal server error", "500", e.getClass().getSimpleName(), e.getMessage(), errors);
    }
}
