package com.khaled_amin.book_social_network.identity.user.account.domain.command;


import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EmailAddress;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Username;


public record AccountCreateCommand(
        ActorCode accountCode,
        Username username,
        EncodedPassword encodedPassword,
        EmailAddress emailAddress
) {

    public static AccountCreateCommand  of(
            ActorCode accountCode,
            String username,
            String encodedPassword,
            String email
    ) {
        return new AccountCreateCommand(
                accountCode,
                Username.of(username),
                EncodedPassword.of(encodedPassword),
                EmailAddress.of(email)
        );
    }

}