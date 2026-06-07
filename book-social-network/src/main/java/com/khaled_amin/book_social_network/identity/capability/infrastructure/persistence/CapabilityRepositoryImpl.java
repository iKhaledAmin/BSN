package com.khaled_amin.book_social_network.identity.capability.infrastructure.persistence;

import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.repository.CapabilityRepository;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
@AllArgsConstructor
public class CapabilityRepositoryImpl implements CapabilityRepository {

    private final CapabilityJpaRepository capabilityJpaRepository;

    @Override
    public Capability save(Capability capability) {
        return capabilityJpaRepository.save(capability);
    }

    @Override
    public boolean existsByCode(CapabilityCode code) {
        return capabilityJpaRepository.existsByCode(code.value());
    }

    @Override
    public Optional<Capability> findByCode(CapabilityCode code) {
        return capabilityJpaRepository.findByCode(code.value());
    }

    @Override
    public Optional<Capability> findByCodeAndModule(CapabilityCode code, SystemDomain module) {
        return capabilityJpaRepository.findByCodeAndModule(code.value(),module.name());
    }

    @Override
    public boolean existsByCodeAndModule(CapabilityCode code, SystemDomain module) {
        return capabilityJpaRepository.existsByCodeAndModule(code.value(),module.name());
    }

    @Override
    public List<Capability> findAllByModule(SystemDomain module) {
        return capabilityJpaRepository.findAllByModule(module);
    }

    @Override
    public List<Capability> findAll() {
        return capabilityJpaRepository.findAll();
    }

}
