package org.example.scrapper.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType, String queue, String exchange,
                                String routingKey, boolean useQueue) {
    public record Scheduler(Duration interval) {
    }
}
