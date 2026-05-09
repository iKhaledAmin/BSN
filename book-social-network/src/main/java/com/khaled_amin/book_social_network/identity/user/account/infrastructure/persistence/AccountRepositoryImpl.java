package com.khaled_amin.book_social_network.identity.user.account.infrastructure.persistence;

import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountJpaRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountJpaRepository.existsByEmailAddress(email);
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return accountJpaRepository.existsByAccountRolesRoleName(roleName);
    }

    @Override
    public boolean existsByRoleId(Long roleId) {
        return accountJpaRepository.existsByAccountRolesRoleId(roleId);
    }

    @Override
    public long countByRoleName(String roleName) {
        return accountJpaRepository.countByAccountRolesRoleName(roleName);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountJpaRepository.findByEmailAddress(email);
    }
}
