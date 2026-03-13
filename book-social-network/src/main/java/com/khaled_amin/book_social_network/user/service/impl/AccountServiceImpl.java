package com.khaled_amin.book_social_network.user.service.impl;

import com.khaled_amin.book_social_network.common.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.service.RoleService;
import com.khaled_amin.book_social_network.user.exception.AccountException;
import com.khaled_amin.book_social_network.user.factory.AccountFactory;
import com.khaled_amin.book_social_network.user.model.dto.AccountRequest;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import com.khaled_amin.book_social_network.user.model.mapper.AccountMapper;
import com.khaled_amin.book_social_network.user.repository.AccountRepo;
import com.khaled_amin.book_social_network.user.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountFactory accountFactory;
    private final AccountMapper accountMapper;
    private final RoleService roleService;
    private final EntityRetrievalService entityRetrievalService;




    @Transactional
    @Override
    public Account register(RegistrationRequest request) {

        validateRegistration(request);

        Role defaultRole = roleService.getDefaultRole();

        Account account = accountFactory.createAccount(request, defaultRole);

        return accountRepo.save(account);
    }



    @Transactional
    @Override
    public Account update(Long accountId, AccountRequest request) {

        Account account = getById(accountId);

        validateAccountUpdate(account, request);

        accountMapper.updateEntity(request, account);

        return accountRepo.save(account);
    }

    // -------------------------------- RETRIEVAL -------------------------------- //

    @Override
    public Optional<Account> getOptionalById(Long id) {
        return entityRetrievalService.getOptionalById(Account.class, id);
    }

    @Override
    public Account getById(Long id) {
        return entityRetrievalService.getById(
                Account.class,
                id,
                AccountException::notFound
        );
    }

    @Override
    public Optional<Account> getOptionalByUsername(String username) {
        return accountRepo.findByUsername(username);
    }

    @Override
    public Account getByUsername(String username) {
        return getOptionalByUsername(username)
                .orElseThrow(AccountException::notFound);
    }




    // -------------------------------- VALIDATION -------------------------------- //

    private void validateRegistration(RegistrationRequest request) {

        ensureUsernameAvailable(request.getUsername());
        ensureEmailAvailable(request.getEmailAddress());

    }

    private void validateAccountUpdate(Account account, AccountRequest request) {

        if (!account.getUsername().equals(request.getUsername())) {
            ensureUsernameAvailable(request.getUsername());
        }

        if (!account.getEmailAddress().equals(request.getEmailAddress())) {
            ensureEmailAvailable(request.getEmailAddress());
        }
    }

    private void ensureUsernameAvailable(String username) {

        if (accountRepo.existsByUsername(username)) {
            throw AccountException.usernameAlreadyExists(username);
        }

    }

    private void ensureEmailAvailable(String email) {

        if (accountRepo.existsByEmailAddress(email)) {
            throw AccountException.emailAlreadyExists(email);
        }

    }

    // -------------------------------- BUILDERS -------------------------------- //



    // -------------------------------- ROLE MANAGEMENT -------------------------------- //

}
