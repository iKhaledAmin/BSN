package com.khaled_amin.book_social_network.user.service;

import com.khaled_amin.book_social_network.user.model.dto.AccountRequest;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import jakarta.validation.Valid;

import java.util.Optional;

public interface AccountService {

    Account register(@Valid RegistrationRequest request);

    Account update(Long accountId, AccountRequest request);

    Optional<Account> getOptionalById(Long id);
    Account getById(Long id);

    Optional<Account> getOptionalByUsername(String username);
    Account getByUsername(String username);

}
