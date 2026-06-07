package com.khaled_amin.book_social_network.identity.capability.application.service;

import com.khaled_amin.book_social_network.core.logging.audit.BusinessEventLogger;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.repository.CapabilityRepository;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityBusinessException;
import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityTechnicalException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CapabilityServiceImpl implements CapabilityService {
    private final CapabilityRepository capabilityRepository;
    private final BusinessEventLogger businessEventLogger;

    @Transactional
    @Override
    public Capability create(CapabilityDefinition definition) {
        if (definition == null){
            throw CapabilityTechnicalException.nullDefinition();
        }

        Capability newCapability = Capability.create(definition);
        Capability saved = capabilityRepository.save(newCapability);

        businessEventLogger.capabilityInitialized(
                saved.getCode()
        );

        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public Capability viewCapability(CapabilityCode code) {

        Capability capability = getByCode(code);

        businessEventLogger.capabilityViewed(
                capability.getCode()
        );

        return capability;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Capability> listCapabilities(SystemDomain module) {

        List<Capability> capabilities =
                module == null
                        ? getAll()
                        : getByModule(module);

        businessEventLogger.capabilityListed(
                module != null ? module.name() : "ALL"
        );

        return capabilities;
    }



    // -------------------------------------- read operations -------------------------------------- //

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
    public Optional<Capability> getOptionalByCodeAndModule(CapabilityCode code, SystemDomain module) {
        return capabilityRepository.findByCodeAndModule(code,module);
    }

    @Override
    public Capability getByCodeAndModule(CapabilityCode code, SystemDomain module) {
        return getOptionalByCodeAndModule(code, module)
                .orElseThrow(() -> CapabilityBusinessException.notFound()
                        .withClientDetails("reason", "Capability not found for module")
                        .withClientDetails("code", code.value())
                        .withClientDetails("module", module.name())
                );
    }


    @Override
    public boolean existsByCodeAndModule(CapabilityCode code, SystemDomain module) {
        return capabilityRepository.existsByCodeAndModule(code,module);
    }

    @Override
    public List<Capability> getAll() {
        return capabilityRepository.findAll();
    }

    @Override
    public List<Capability> getByModule(SystemDomain module) {
        return capabilityRepository.findAllByModule(module);
    }



}
