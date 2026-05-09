package com.khaled_amin.book_social_network.identity.user.account.domain.command;


import com.khaled_amin.book_social_network.identity.user.account.domain.value.FirstName;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.LastName;

public record ProfileCreateCommand(
        FirstName firstName,
        LastName lastName

) {
    public static ProfileCreateCommand of(String firstName , String lastName){
        return new ProfileCreateCommand(
                 FirstName.of(firstName),
                 LastName.of(lastName)
        );
    }
}
