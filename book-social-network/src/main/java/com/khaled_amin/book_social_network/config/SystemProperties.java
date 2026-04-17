package com.khaled_amin.book_social_network.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;




@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "system")
public class SystemProperties {

    @Valid
    private AdminProperties admin = new AdminProperties();

    @Getter
    @Setter
    public static class AdminProperties {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        @NotBlank
        private String email;
    }
}