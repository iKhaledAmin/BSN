package com.khaled_amin.book_social_network.email.infrastructure.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "application.email")
public record EmailProperties(
        @NotNull @Valid Retry retry
) {

    public record Retry(
            @NotNull @Valid
            Policy policy,

            @NotNull @Valid
            Scheduler scheduler
    ) {}

    public record Policy(
            @Min(1)
            long backoffSeconds,
            @Min(1)
            int maxAttempts
    ) {}

    public record Scheduler(
            @Min(1)
            long intervalSeconds
    ) {}
}