package com.khaled_amin.book_social_network.user.domain.command;

import com.khaled_amin.book_social_network.user.domain.value.*;

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
