package com.khaled_amin.book_social_network.identity.capability.application.service;

import com.khaled_amin.book_social_network.identity.capability.application.exception.CapabilityApplicationException;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.command.CapabilityUpdateCommand;
import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.repository.CapabilityRepository;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CapabilityServiceImpl implements CapabilityService {
    private final CapabilityRepository capabilityRepository;
    private final ActorProvider actorProvider;

    @Transactional
    @Override
    public Capability create(CapabilityDefinition capability) {
        Capability newCapability = Capability.create(capability);

        return capabilityRepository.save(newCapability);
    }

    @Transactional
    @Override
    public Capability update(CapabilityCode code , CapabilityUpdateCommand command) {
        if (command == null) {
            throw CapabilityDomainException
                    .invalidCommand()
                    .withDetail("reason", "Capability update command must not be null");
        }

        Capability existingCapability = getByCode(code);
        Actor actor = actorProvider.getCurrent();

        validateUpdate(existingCapability,command);

        existingCapability.update(command);

        return capabilityRepository.save(existingCapability);
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
                .orElseThrow(() -> CapabilityApplicationException.notFound()
                .withDetail("reason", "Capability not found for given code")
                .withDetail("code",code)
        );
    }



    @Override
    public Optional<Capability> getOptionalByCodeAndModule(CapabilityCode code, CapabilityModule module) {
        return capabilityRepository.findOptionalByCodeAndModule(code,module);
    }

    @Override
    public Capability getByCodeAndModule(CapabilityCode code, CapabilityModule module) {
        return getOptionalByCodeAndModule(code,module)
                .orElseThrow(() -> CapabilityApplicationException.notFound()
                        //.withDetail()
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




    public void validateUpdate(Capability existing, CapabilityUpdateCommand command) {

        if (command.displayName() != null &&
                !existing.getName().equals(command.displayName().value())) {
            ensureDisplayNameUnique(command.displayName().value());
        }
    }

    private void ensureDisplayNameUnique(String name) {
        if (capabilityRepository.existsByName(name)) {
            throw CapabilityApplicationException
                    .alreadyExists()
                    .withDetail("reason", "Capability name already exist");
        }
    }


}
