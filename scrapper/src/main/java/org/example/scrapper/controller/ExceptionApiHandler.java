package org.example.scrapper.controller;

import org.example.scrapper.dto.responses.ApiErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiErrorResponse response = new ApiErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Chat not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotFoundException(InstanceNotFoundException e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Link not found", "404", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        ApiErrorResponse response = new ApiErrorResponse("Internal server error", "500", e.getClass().getSimpleName(), e.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
