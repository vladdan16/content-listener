package org.example.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType, String queue, String exchange,
                                String routingKey, @NotNull boolean useQueue) {
    public record Scheduler(Duration interval) {
    }
}