package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import lombok.Getter;

@Getter
public enum AccountStatus {

    DISABLED("Account is not active or not yet verified."),
    ACTIVE("Account is active and can login."),
    LOCKED("Account is locked due to security restrictions."),
    SUSPENDED("Account is suspended due to policy or business violations.");


    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    // helper methods
    public static AccountStatus getDefault() {
        return DISABLED;
    }

    public boolean isDisabled() { return this == DISABLED;}

    public boolean isActive() { return this == ACTIVE;}

    public boolean isLocked(){ return this == LOCKED;}

    public boolean isSuspended(){ return this == SUSPENDED;}
}
