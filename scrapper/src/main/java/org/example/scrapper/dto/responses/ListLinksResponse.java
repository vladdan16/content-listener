package org.example.scrapper.dto.responses;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, int size) {
}
