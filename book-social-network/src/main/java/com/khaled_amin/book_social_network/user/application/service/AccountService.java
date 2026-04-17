package com.khaled_amin.book_social_network.user.application.service;

import com.khaled_amin.book_social_network.role.domain.value.RoleId;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.user.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import com.khaled_amin.book_social_network.user.domain.value.AccountId;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account register(AccountCreateCommand command);
    Account create(AccountCreateCommand command,List<Long> roleIds);
    Account update(AccountId accountId, AccountUpdateCommand command);

    Account assignRoles(AccountId accountId, RoleId roleId);
    Account assignRoles(AccountId accountId, List<Long> roleIds);
    Account removeRole(AccountId accountId, RoleId roleId);
    Account replaceRoles(AccountId accountId, List<Long> roleIds);

    Optional<Account> getOptionalById(Long id);
    Account getById(Long id);
    Account getById(AccountId accountId);

    Optional<Account> getOptionalByUsername(String username);
    Account getByUsername(String username);

    boolean existsByRoleName(String roleName);
}
