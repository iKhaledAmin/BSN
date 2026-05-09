package com.khaled_amin.book_social_network.identity.verification.application.config;

import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Setter
@ConfigurationProperties(prefix = "application.verification-token")
@Validated
public class VerificationProperties {

    @NotNull(message = "Verification configs must not be null")
    private Map<TokenType, @Valid TokenConfig> verificationConfigs;

    public int getCodeLength(TokenType type) {
        return getExpirationConfig(type).codeLength();
    }

    public int getExpirationMinutes(TokenType type) {
        return getExpirationConfig(type).expirationMinutes();
    }

    private TokenConfig getExpirationConfig(TokenType type) {
        TokenConfig config = verificationConfigs.get(type);

        if (config == null) {
            throw new IllegalStateException(
                    "Verification expiration config not found for token type: " + type
            );
        }

        return config;
    }

    @PostConstruct
    public void validate() {

        if (verificationConfigs == null || verificationConfigs.isEmpty()) {
            throw new IllegalStateException(
                    "Verification expiration configs must not be null or empty"
            );
        }

        for (TokenType type : TokenType.values()) {
            if (!verificationConfigs.containsKey(type)) {
                throw new IllegalStateException(
                        "Missing verification config for token type: " + type
                );
            }
        }
    }

    public record TokenConfig(

            @Min(value = 4, message = "Code length must be at least 4")
            int codeLength,

            @Min(value = 1, message = "Expiration must be at least 1 minute")
            int expirationMinutes
    ) {}
}