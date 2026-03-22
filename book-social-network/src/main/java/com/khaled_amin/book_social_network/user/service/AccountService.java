package com.khaled_amin.book_social_network.user.service;

import com.khaled_amin.book_social_network.user.model.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account register(@Valid RegistrationRequest request);

    Account update(Long accountId, AccountUpdateRequest request);

    Account assignRole(Long accountId, Long roleId);
    Account removeRole(Long accountId, Long roleId);
    Account setRoles(Long accountId, List<Long> roleIds);

    Optional<Account> getOptionalById(Long id);
    Account getById(Long id);

    Optional<Account> getOptionalByUsername(String username);
    Account getByUsername(String username);

}
