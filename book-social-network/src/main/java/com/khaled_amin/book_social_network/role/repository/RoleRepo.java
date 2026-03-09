package com.khaled_amin.book_social_network.role.repository;

import com.khaled_amin.book_social_network.role.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);

    Boolean existsByName(String name);
}
