package com.khaled_amin.book_social_network.user.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileStatus {

    GUEST("Temporary profile created automatically."),
    INCOMPLETE("Registered user, profile information not finished."),
    COMPLETE("Full profile information provided.");

    private final String description;

    public static ProfileStatus getDefault() {
        return INCOMPLETE;
    }

    public  boolean isComplete() { return this == COMPLETE;}

    public boolean isIncomplete() { return this == INCOMPLETE;}

    public boolean isGuest() {return this == GUEST;}
}