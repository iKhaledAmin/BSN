package com.khaled_amin.book_social_network.identity.capability.application.port;

import com.khaled_amin.book_social_network.identity.capability.domain.command.CapabilityUpdateCommand;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;

import java.util.List;
import java.util.Optional;

public interface CapabilityService {

    Capability create(CapabilityDefinition capability);

    Capability update(CapabilityCode code , CapabilityUpdateCommand command);

    boolean existsByCode(CapabilityCode code);

    Optional<Capability> getOptionalByCode(CapabilityCode code);
    Capability getByCode(CapabilityCode code);

    Optional<Capability> getOptionalByCodeAndModule(CapabilityCode code, CapabilityModule module);
    Capability getByCodeAndModule(CapabilityCode code, CapabilityModule module);
    boolean existsByCodeAndModule(CapabilityCode code, CapabilityModule module);

    List<Capability> getAll();

    List<Capability> getByModule(CapabilityModule module);

}
