package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;

public record AccountId(Long value) {

    public AccountId {
        if (value == null) {
            throw  AccountDomainException
                    .invalidAccountId()
                    .withDetail("reason", "Account id  must not be null");
        }
    }

    public static AccountId of(Long value) {
        return new AccountId(value);
    }
}