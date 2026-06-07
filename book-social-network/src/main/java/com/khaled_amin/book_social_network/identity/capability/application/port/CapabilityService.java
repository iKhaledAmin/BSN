package com.khaled_amin.book_social_network.identity.capability.application.port;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;

import java.util.List;
import java.util.Optional;

public interface CapabilityService {

    Capability create(CapabilityDefinition capability);


    Capability viewCapability(CapabilityCode code);
    List<Capability> listCapabilities(SystemDomain module);

    boolean existsByCode(CapabilityCode code);

    Optional<Capability> getOptionalByCode(CapabilityCode code);
    Capability getByCode(CapabilityCode code);

    List<Capability> getByModule(SystemDomain module);
    Optional<Capability> getOptionalByCodeAndModule(CapabilityCode code, SystemDomain module);
    Capability getByCodeAndModule(CapabilityCode code, SystemDomain module);
    boolean existsByCodeAndModule(CapabilityCode code, SystemDomain module);

    List<Capability> getAll();


}
