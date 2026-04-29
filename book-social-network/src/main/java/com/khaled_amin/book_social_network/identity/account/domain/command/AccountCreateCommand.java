package com.khaled_amin.book_social_network.user.domain.command;

import com.khaled_amin.book_social_network.user.domain.value.*;


public record AccountCreateCommand(
        Username username,
        EncodedPassword encodedPassword,
        Email email,
        ProfileCreateCommand profileCommand
) {

    public static AccountCreateCommand of(
            String username,
            String encodedPassword,
            String email,
            String firstName,
            String lastName
    ) {
        return new AccountCreateCommand(
                Username.of(username),
                EncodedPassword.of(encodedPassword),
                Email.of(email),
                ProfileCreateCommand.of(firstName, lastName)
        );
    }

}