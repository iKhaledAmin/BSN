package com.khaled_amin.book_social_network.identity.user.account.application.service;

import com.khaled_amin.book_social_network.core.pagination.PageResult;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountCreateRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountPageRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.RawPassword;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;


import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account create(AccountCreateRequest request);
    Account update(ActorCode accountCode, AccountUpdateRequest request);

    Account activate(ActorCode accountCode);

    void resetPassword(ActorCode accountCode, RawPassword rawPassword);

    Account assignRole(ActorCode accountCode, RoleName roleName);
    Account assignRoles(ActorCode accountCode, List<RoleName> roleNames);
    Account removeRole(ActorCode accountCode, RoleName roleName);
    Account replaceRoles(ActorCode accountCode, List<RoleName> roleNames);

    void login(ActorCode accountCode);

    Account viewAccount(ActorCode accountCode);
    Account viewMyAccount();
    PageResult<Account> listAccounts(AccountPageRequest request);


    boolean existsByRoleName(RoleName roleName);

    Optional<Account> getOptionalByEmail(String email);

    Optional<Account> getOptionalByUsername(String username);

    Optional<Account> getOptionalByAccountCode(ActorCode accountCode);
    Account getByAccountCode(ActorCode accountCode);

    Account getByIdentity(ActorIdentity identity);

    PageResult<Account> getAll(AccountPageRequest request);


}
