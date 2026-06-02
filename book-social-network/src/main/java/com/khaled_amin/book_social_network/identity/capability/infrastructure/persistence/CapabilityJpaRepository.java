package com.khaled_amin.book_social_network.identity.capability.infrastructure.persistence;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;

import java.util.List;
import java.util.Optional;

public interface CapabilityJpaRepository extends BaseRepository<Capability, Long> {
    boolean existsByCode(String code);
    Optional<Capability> findByCode(String code);

    List<Capability> findAllByModule(CapabilityModule module);

    boolean existsByCodeAndModule(String name, String module);
    Optional<Capability> findByCodeAndModule(String name, String module);
}
