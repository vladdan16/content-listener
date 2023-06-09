package org.example.scrapper.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowResponse(String title, @JsonProperty("updated_at") OffsetDateTime updatedAt) {
}
