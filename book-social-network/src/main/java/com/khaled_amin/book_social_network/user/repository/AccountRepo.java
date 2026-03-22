package com.khaled_amin.book_social_network.user.repository;

import com.khaled_amin.book_social_network.common.repository.BaseRepository;
import com.khaled_amin.book_social_network.user.model.entity.Account;


import java.util.List;
import java.util.Optional;

public interface AccountRepo extends BaseRepository<Account,Long> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String emailAddress);


    List<Account> findByAccountRolesRoleName(String roleName);

    long countByAccountRolesRoleSystemCode(String systemCode);

    boolean existsByAccountRolesRoleSystemCode(String systemCode);
}
