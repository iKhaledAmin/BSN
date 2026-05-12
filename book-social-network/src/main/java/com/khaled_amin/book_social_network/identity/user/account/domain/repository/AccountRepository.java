package com.khaled_amin.book_social_network.identity.user.account.domain.repository;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByRoleName(String roleName);

    boolean existsByRoleId(Long roleId);

    long countByRoleName(String roleName);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByAccountCode(String accountCode);

    Optional<Account> findByRoleName(String roleName);
}