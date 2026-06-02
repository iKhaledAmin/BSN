package com.khaled_amin.book_social_network.identity.user.account.domain.command;

import com.khaled_amin.book_social_network.identity.user.account.domain.value.EmailAddress;

import java.util.Optional;

public record AccountUpdateCommand(
        Optional<EmailAddress> email,
        Optional<ProfileUpdateCommand> profileCommand
) {

    public static AccountUpdateCommand of(String email, ProfileUpdateCommand profileCommand) {
        return new AccountUpdateCommand(
                Optional.ofNullable(email).map(EmailAddress::of),
                Optional.ofNullable(profileCommand)
        );
    }
}