package com.Khaled_Amin.book_social_network.user.model.enums;

import lombok.Getter;

@Getter
public enum ProfileStatus {

    GUEST("Temporary profile created automatically."),
    INCOMPLETE("Registered user, profile information not finished."),
    COMPLETE("Full profile information provided.");

    private final String description;

    ProfileStatus(String description) {
        this.description = description;
    }



    public boolean isComplete() {
        return this == COMPLETE;
    }

    public boolean isGuest() {
        return this == GUEST;
    }
}