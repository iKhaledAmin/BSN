package com.khaled_amin.book_social_network.identity.user.account.domain.repository;

import com.khaled_amin.book_social_network.core.pagination.PageResult;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountPageRequest;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByRoleName(RoleName roleName);

    boolean existsByRoleId(Long roleId);

    long countByRoleName(String roleName);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByAccountCode(String accountCode);

    PageResult<Account> findAll(AccountPageRequest request);
}