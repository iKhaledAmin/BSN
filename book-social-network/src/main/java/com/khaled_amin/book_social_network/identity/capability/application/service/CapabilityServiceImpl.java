package com.khaled_amin.book_social_network.identity.capability.application.service;

import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.repository.CapabilityRepository;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityBusinessException;
import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityTechnicalException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CapabilityServiceImpl implements CapabilityService {
    private final CapabilityRepository capabilityRepository;

    @Transactional
    @Override
    public Capability create(CapabilityDefinition definition) {
        if (definition == null){
            throw CapabilityTechnicalException.nullDefinition();
        }

        Capability newCapability = Capability.create(definition);
        return capabilityRepository.save(newCapability);
    }


    @Override
    public boolean existsByCode(CapabilityCode code) {
        return capabilityRepository.existsByCode(code);
    }

    @Override
    public Optional<Capability> getOptionalByCode(CapabilityCode code){
        return capabilityRepository.findByCode(code);
    }

    @Override
    public Capability getByCode(CapabilityCode code) {
        return getOptionalByCode(code)
                .orElseThrow(() -> CapabilityBusinessException.notFound()
                        .withClientDetails("reason", "Capability not found")
                        .withClientDetails("code", code.value())
                );
    }



    @Override
    public Optional<Capability> getOptionalByCodeAndModule(CapabilityCode code, CapabilityModule module) {
        return capabilityRepository.findByCodeAndModule(code,module);
    }

    @Override
    public Capability getByCodeAndModule(CapabilityCode code, CapabilityModule module) {
        return getOptionalByCodeAndModule(code, module)
                .orElseThrow(() -> CapabilityBusinessException.notFound()
                        .withClientDetails("reason", "Capability not found for module")
                        .withClientDetails("code", code.value())
                        .withClientDetails("module", module.name())
                );
    }


    @Override
    public boolean existsByCodeAndModule(CapabilityCode code, CapabilityModule module) {
        return capabilityRepository.existsByCodeAndModule(code,module);
    }

    @Override
    public List<Capability> getAll() {
        return capabilityRepository.findAll();
    }

    @Override
    public List<Capability> getByModule(CapabilityModule module) {
        return capabilityRepository.findAllByModule(module);
    }



}
