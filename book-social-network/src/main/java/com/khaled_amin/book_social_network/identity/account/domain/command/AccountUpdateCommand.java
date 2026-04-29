package com.khaled_amin.book_social_network.user.domain.command;

import com.khaled_amin.book_social_network.user.domain.value.Email;

import java.util.Optional;

public record AccountUpdateCommand(
        Optional<Email> email,
        Optional<ProfileUpdateCommand> profileCommand
) {

    public static AccountUpdateCommand of(String email, ProfileUpdateCommand profileCommand) {
        return new AccountUpdateCommand(
                Optional.ofNullable(email).map(Email::of),
                Optional.ofNullable(profileCommand)
        );
    }
}