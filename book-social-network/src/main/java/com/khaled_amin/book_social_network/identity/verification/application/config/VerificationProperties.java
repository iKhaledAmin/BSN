package com.khaled_amin.book_social_network.identity.verification.application.config;

import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Setter
@ConfigurationProperties(prefix = "application.verification-token")
public class VerificationProperties {

    private Map<TokenType, TokenConfig> verificationConfigs;


    public int getCodeLength(TokenType type) {
        return getConfig(type).codeLength();
    }

    public int getExpirationMinutes(TokenType type) {
        return getConfig(type).expirationMinutes();
    }


    private TokenConfig getConfig(TokenType type) {
        if (this.verificationConfigs == null) {
            throw new IllegalStateException("Verification configs not initialized");
        }

        TokenConfig tokenConfig = verificationConfigs.get(type);

        if (tokenConfig == null) {
            throw new IllegalStateException("Missing config for token type: " + type);
        }

        return tokenConfig;
    }

    public record TokenConfig(int codeLength, int expirationMinutes) {}

}