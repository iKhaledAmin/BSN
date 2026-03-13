package com.khaled_amin.book_social_network.role.repository;

import com.khaled_amin.book_social_network.common.repository.BaseRepository;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepo extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Role r SET r.defaultRole = false WHERE r.defaultRole = true")
    void clearDefaultRole();

    @Modifying
    @Query("""
       UPDATE Role r 
       SET r.defaultRole = CASE 
           WHEN r.id = :roleId THEN true 
           ELSE false 
       END
       """)
    void assignDefaultRole(Long roleId);

    Optional<Role> findByDefaultRoleTrue();
}
