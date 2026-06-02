package com.khaled_amin.book_social_network.security.jwt;

import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.exception.SecurityTechnicalException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@ConfigurationProperties(prefix = "application.security.jwt")
@Setter
@Validated
public class JwtProperties {

    @Getter
    @NotBlank(message = "JWT secret key must not be blank")
    @Size(min = 32, message = "JWT secret key must be at least 32 characters")
    private String secretKey;

    @NotNull(message = "JWT expiration configs must not be null")
    private Map<ActorType, @Valid ExpirationConfig> expirationConfigs;

    @PostConstruct
    public void validate() {

        if (expirationConfigs == null || expirationConfigs.isEmpty()) {

            throw SecurityTechnicalException.invalidConfiguration(
                    "JWT expiration configs must not be null or empty"
            );
        }

        for (ActorType type : ActorType.values()) {

            if (type == ActorType.SYSTEM || type == ActorType.ANONYMOUS) {
                continue;
            }

            if (!expirationConfigs.containsKey(type)) {

                throw SecurityTechnicalException.invalidConfiguration(
                        "Missing JWT expiration config for actor type: " + type
                );
            }
        }
    }

    public long getExpirationMinutes(ActorType type) {
        return getConfig(type).expirationMinutes();
    }

    private ExpirationConfig getConfig(ActorType type) {

        ExpirationConfig expirationConfig = expirationConfigs.get(type);

        if (expirationConfig == null) {

            throw SecurityTechnicalException.invalidConfiguration(
                    "JWT expiration config not found for actor type: " + type
            );
        }

        return expirationConfig;
    }

    public record ExpirationConfig(
            @Min(value = 1, message = "Expiration must be at least 1 minute")
            long expirationMinutes
    ) {}
}