package com.khaled_amin.book_social_network.identity.user.account.application.validation;

import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountCreateRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountBusinessException;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AccountApplicationValidator {

    private final AccountRepository accountRepository;

    // ---------------- CREATE ---------------- //


    public void validateCreate(AccountCreateRequest request) {
        ensureUsernameUnique(request.getUsername());
        ensureEmailUnique(request.getEmailAddress());
    }
    // ---------------- UPDATE ---------------- //

    public void validateUpdate(Account account, AccountUpdateRequest request) {
        String emailAddress = request.getEmailAddress();
        if (emailAddress != null && !account.getEmailAddress().equals(emailAddress)){
            ensureEmailUnique(emailAddress);
        }
    }


    // ---------------- PRIVATE ---------------- //

    private void ensureUsernameUnique(String username) {

        if (accountRepository.existsByUsername(username)) {
            throw AccountBusinessException.usernameAlreadyExists()
                    .withClientDetails("username", username);
        }
    }

    private void ensureEmailUnique(String email) {

        if (accountRepository.existsByEmail(email)) {
            throw AccountBusinessException.emailAlreadyExists()
                    .withClientDetails("emailAddress", email);
        }
    }


}