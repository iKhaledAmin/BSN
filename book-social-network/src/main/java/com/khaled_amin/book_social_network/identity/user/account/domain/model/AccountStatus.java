package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import lombok.Getter;

@Getter
public enum AccountStatus {

    PENDING_VERIFICATION("Account is awaiting email verification."),
    ACTIVE("Account is active and can login."),
    LOCKED("Account is temporarily locked due to security restrictions."),
    SUSPENDED("Account is suspended due to policy violations."),
    DISABLED("Account is administratively disabled.");


    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    // helper methods
    public static AccountStatus getDefault() {
        return PENDING_VERIFICATION;
    }

    public boolean isActive() { return this == ACTIVE;}

    public boolean isLocked(){ return this == LOCKED;}

    public boolean isSuspended(){ return this == SUSPENDED;}

    public boolean isDisabled(){ return this == DISABLED;}

}
