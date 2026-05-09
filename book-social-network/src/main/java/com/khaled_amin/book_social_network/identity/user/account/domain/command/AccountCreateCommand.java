package com.khaled_amin.book_social_network.identity.user.account.domain.command;


import com.khaled_amin.book_social_network.identity.user.account.domain.value.Email;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Username;


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