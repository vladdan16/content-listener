package org.example.scrapper.dto.requests;

import java.util.List;

public record LinkUpdateRequest(Long id, String url, String descriptions, List<Long> tgChatIds) {
}
