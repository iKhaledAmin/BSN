package com.khaled_amin.book_social_network.identity.user.account.application.service;

import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Email;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleId;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.AccountId;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account create(AccountCreateCommand command,List<Role> roles);
    Account update(AccountId accountId, AccountUpdateCommand command);

    Account activate(AccountId accountId);

    void resetPassword(AccountId accountId, EncodedPassword encodedPassword);

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

    Optional<Account> getOptionalByEmail(String email);
    Optional<Account> getOptionalByEmail(Email email);
    Account getByEmail(String email);
    Account getByEmail(Email email);
    Account getByIdentity(ActorIdentity identity);
}
