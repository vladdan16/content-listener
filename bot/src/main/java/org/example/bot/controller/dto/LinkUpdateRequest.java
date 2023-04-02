package org.example.bot.controller.dto;

import java.util.List;

public record LinkUpdateRequest(Long id, String url, String descriptions, List<Long> tgChatIds) {
}
