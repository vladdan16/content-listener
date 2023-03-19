package org.example.bot.dto;

import java.util.List;

public record LinkUpdateErrorResponse(String description, String code, String exceptionName, String exceptionMessage,
                                      List<String> stacktrace) implements LinkUpdateResponse {
}
