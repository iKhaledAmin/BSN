package com.khaled_amin.book_social_network.identity.user.account.infrastructure.persistence;

import com.khaled_amin.book_social_network.core.pagination.PageResult;
import com.khaled_amin.book_social_network.core.pagination.PageResultFactory;
import com.khaled_amin.book_social_network.core.pagination.PageableFactory;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountPageRequest;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public boolean existsByRoleName(RoleName roleName) {
        return accountJpaRepository.existsByAccountRolesRoleName(roleName.toString());
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

    @Override
    public Optional<Account> findByAccountCode(String accountCode) {
        return accountJpaRepository.findByAccountCodeValue(accountCode);
    }

    @Override
    public PageResult<Account> findAll(AccountPageRequest request) {

        Page<Account> page = accountJpaRepository.findAll(
                PageableFactory.from(request)
        );

        return PageResultFactory.from(page);
    }

}
