package org.example.bot.client.dto;

import java.net.URI;
import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, int size) {
}
