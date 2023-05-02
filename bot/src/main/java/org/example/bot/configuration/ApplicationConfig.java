package org.example.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(@NotNull String test, @NotNull String token, String queue, String exchange,
                                String routingKey, @NotNull boolean useQueue) {
}
