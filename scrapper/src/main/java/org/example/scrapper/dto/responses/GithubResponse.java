package org.example.scrapper.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GithubResponse(String name, @JsonProperty("updated_at")OffsetDateTime updatedAt) {
}
