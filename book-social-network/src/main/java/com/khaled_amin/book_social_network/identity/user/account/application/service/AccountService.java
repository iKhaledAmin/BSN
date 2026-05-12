package com.khaled_amin.book_social_network.identity.user.account.application.service;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Email;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.AccountId;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account create(AccountCreateCommand command,List<Role> roles);
    Account update(ActorCode accountCode, AccountUpdateCommand command);

    Account activate(ActorCode accountCode);

    void resetPassword(ActorCode accountCode, EncodedPassword encodedPassword);

    Account assignRole(ActorCode accountCode, RoleName roleName);
    Account assignRoles(ActorCode accountCode, List<String> roleNames);
    Account removeRole(ActorCode accountCode, RoleName roleName);
    Account replaceRoles(ActorCode accountCode, List<String> roleNames);

    Optional<Account> getOptionalById(Long id);
    Account getById(Long id);
    Account getById(AccountId accountId);

    Optional<Account> getOptionalByUsername(String username);
    Account getByUsername(String username);

    boolean existsByRoleName(String roleName);

    Optional<Account> getOptionalByEmail(String email);
    Account getByEmail(String email);
    Account getByEmail(Email email);
    Account getByIdentity(ActorIdentity identity);
    Optional<Account> getOptionalByAccountCode(ActorCode accountCode);
    Account getByAccountCode(ActorCode accountCode);

    Optional<Account> getOptionalByRoleName(String roleName);
}
