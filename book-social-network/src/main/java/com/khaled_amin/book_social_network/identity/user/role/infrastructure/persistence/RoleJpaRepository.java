package com.khaled_amin.book_social_network.identity.user.role.infrastructure.persistence;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;


import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends BaseRepository<Role, Long> {

    Optional<Role> findByName(String name);

    List<Role> findAllByDefaultRoleTrue();

    boolean existsByDisplayName(String roleDisplayName);
    boolean existsByName(String name);

    List<Role> findAllByNameIn(List<String> names);
}
