package com.khaled_amin.book_social_network.auth.account.application.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "application.auth")
@Validated
public record AuthenticationProperties(@NotNull @Valid Activation activation) {

    public record Activation(
            @NotBlank(message = "Frontend activation URL must not be blank")
            @Pattern(
                    regexp = "^(http|https)://.*$",
                    message = "Frontend URL must be a valid HTTP/HTTPS URL"
            )
            String frontendUrl

    ) {}
}