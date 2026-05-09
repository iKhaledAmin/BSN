package com.khaled_amin.book_social_network.identity.client.domain.model;



public enum ClientStatus {
    DISABLED("---"),
    ACTIVE("---"),
    LOCKED("---");



    private final String description;

    ClientStatus(String description) {
        this.description = description;
    }

    // helper methods
    public static ClientStatus getDefault() {
        return DISABLED;
    }

    public boolean isDisabled() { return this == DISABLED;}

    public boolean isActive() { return this == ACTIVE;}

    public boolean isLocked(){ return this == LOCKED;}

}