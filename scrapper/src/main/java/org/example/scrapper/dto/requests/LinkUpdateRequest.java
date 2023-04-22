package org.example.scrapper.dto.requests;

import java.net.URI;
import java.util.List;

public record LinkUpdateRequest(Long id, URI url, String descriptions, List<Long> tgChatIds) {
}
