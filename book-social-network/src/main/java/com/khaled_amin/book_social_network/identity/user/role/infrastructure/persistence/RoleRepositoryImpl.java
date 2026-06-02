package com.khaled_amin.book_social_network.identity.user.role.infrastructure.persistence;

import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;


    @Override
    public Role save(Role role) {
        return roleJpaRepository.save(role);
    }

    @Override
    public void delete(Role role) {
        roleJpaRepository.delete(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name);
    }

    @Override
    public List<Role> findDefaultRoles() {
        return roleJpaRepository.findAllByDefaultRoleTrue();
    }


    @Override
    public boolean existsByName(String name) {
        return roleJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByDisplayName(String displayName) {
        return roleJpaRepository.existsByDisplayName(displayName);
    }


    @Override
    public List<Role> findAllByNameIn(List<String> names) {
        return roleJpaRepository.findAllByNameIn(names);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll();
    }
}
