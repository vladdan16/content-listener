package org.example.scrapper.dto.responses;

import java.sql.Timestamp;

public record LinkResponse(Long id, String url, Timestamp updatedAt) {
}
