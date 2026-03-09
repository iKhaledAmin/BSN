package com.Khaled_Amin.book_social_network.user.model.enums;

import lombok.Getter;

@Getter
public enum AccountStatus {

    DISABLED("Account is not active or not yet verified."),
    ACTIVE("Account is active and can authenticate."),
    LOCKED("Account is locked due to security restrictions."),
    SUSPENDED("Account is suspended due to policy or business violations.");


    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public boolean canLogin() {
        return this == ACTIVE;
    }
}