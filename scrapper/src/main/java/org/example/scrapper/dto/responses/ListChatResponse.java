package org.example.scrapper.dto.responses;

import java.util.List;

public record ListChatResponse(List<ChatResponse> chats, int size) {
}
