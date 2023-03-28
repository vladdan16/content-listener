package org.example.bot.controller;

import org.example.bot.dto.ApiErrorResponse;
import org.example.bot.dto.LinkUpdateResponse;
import org.example.bot.dto.LinkUpdateRequest;
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
    public void processLinkUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        // TODO: Process link update

        //return ResponseEntity.ok(new LinkUpdateResponse("Update processed"));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new ApiErrorResponse("Validation failed", "400", e.getClass().getSimpleName(), e.getMessage(), errors);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception e) {
        List<String> errors = List.of(Arrays.toString(e.getStackTrace()));
        return new ApiErrorResponse("Internal server error", "500", e.getClass().getSimpleName(), e.getMessage(), errors);
    }
}
