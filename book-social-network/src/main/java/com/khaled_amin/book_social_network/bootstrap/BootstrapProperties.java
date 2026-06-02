package com.khaled_amin.book_social_network.bootstrap;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "application.bootstrap")
@Validated
public record BootstrapProperties(@NotNull @Valid Admin admin) {

    public record Admin(

            @NotBlank(message = "Admin username must not be blank")
            @Size(min = 3, max = 50, message = "Admin username must be between 3 and 50 characters")
            String username,

            @NotBlank(message = "Admin password must not be blank")
            @Size(min = 6, message = "Admin password must be at least 6 characters")
            String password,

            @NotBlank(message = "Admin emailAddress must not be blank")
            @Email(message = "Admin emailAddress must be valid")
            String email

    ) {}
}