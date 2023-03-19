package org.example.bot.dto;

import java.util.List;

public record LinkUpdateRequest(Integer id, String url, String descriptions, List<Integer> tgChatIds) {
}
