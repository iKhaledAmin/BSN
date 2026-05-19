package com.khaled_amin.book_social_network.identity.capability.domain.repository;

import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;

import java.util.List;
import java.util.Optional;


public interface CapabilityRepository {
    Capability save(Capability capability);

    boolean existsByCode(CapabilityCode code);

    Optional<Capability> findByCode(CapabilityCode code);
    
    Optional<Capability> findOptionalByCodeAndModule(CapabilityCode code, CapabilityModule module);
    
    boolean existsByCodeAndModule(CapabilityCode code, CapabilityModule module);

    List<Capability> findAllByModule(CapabilityModule module);

    List<Capability> findAll();

    boolean existsByName(String name);
}
