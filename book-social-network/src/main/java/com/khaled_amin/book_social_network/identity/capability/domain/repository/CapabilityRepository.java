package com.khaled_amin.book_social_network.identity.capability.domain.repository;

import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;

import java.util.List;
import java.util.Optional;


public interface CapabilityRepository {
    Capability save(Capability capability);

    boolean existsByCode(CapabilityCode code);

    Optional<Capability> findByCode(CapabilityCode code);

    Optional<Capability> findByCodeAndModule(CapabilityCode code, SystemDomain module);
    
    boolean existsByCodeAndModule(CapabilityCode code, SystemDomain module);

    List<Capability> findAllByModule(SystemDomain module);

    List<Capability> findAll();
}
