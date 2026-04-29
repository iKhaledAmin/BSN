package com.khaled_amin.book_social_network.user.infrastructure.persistence;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;
import com.khaled_amin.book_social_network.user.domain.model.Account;

import java.util.Optional;

public interface AccountJpaRepository  extends BaseRepository<Account,Long> {

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String emailAddress);

    long countByAccountRolesRoleName(String roleName);

    boolean existsByAccountRolesRoleName(String roleName);

    boolean existsByAccountRolesRoleId(Long roleId);
}
