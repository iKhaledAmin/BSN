package com.khaled_amin.book_social_network.identity.verification.domain.model;

public enum TokenType {
    ACCOUNT_ACTIVATION,
    ACCOUNT_RESET_PASSWORD;

    public boolean same(TokenType other) {
        return this == other;
    }
}
