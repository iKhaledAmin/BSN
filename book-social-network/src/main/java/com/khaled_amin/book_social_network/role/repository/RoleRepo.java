package com.khaled_amin.book_social_network.role.repository;

import com.khaled_amin.book_social_network.common.repository.BaseRepository;
import com.khaled_amin.book_social_network.role.model.entity.Role;


import java.util.List;
import java.util.Optional;

public interface RoleRepo extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Boolean existsByName(String name);

    List<Role> findAllByDefaultRoleTrue();

    boolean existsBySystemCode(String systemCode);

    Optional<Role> findBySystemCode(String systemCode);
}
