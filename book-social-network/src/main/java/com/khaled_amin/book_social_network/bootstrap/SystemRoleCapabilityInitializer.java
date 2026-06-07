package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.core.utils.diff.DiffResult;
import com.khaled_amin.book_social_network.core.utils.diff.DiffUtils;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.role.domain.definition.SystemRoleCapabilityDefinition;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j(topic = "SystemRoleCapabilityInitializer")
@Component
@Order(InitializerOrder.ROLE_CAPABILITY)
@RequiredArgsConstructor
public class SystemRoleCapabilityInitializer implements CommandLineRunner {

    private final List<SystemRoleCapabilityDefinition> definitions;
    private final RoleService roleService;
    private final CapabilityService capabilityService;

    @Override
    @Transactional
    public void run(String... args) {

        log.info("Starting system role capability synchronization");

        for (SystemRoleCapabilityDefinition definition : definitions) {
            synchronize(definition);
        }

        log.info("Finished system role capability synchronization");
    }

    private void synchronize(SystemRoleCapabilityDefinition definition) {

        Role role = roleService.getByName(
                definition.getRole().getName()
        );

        Set<Capability> targetCapabilities = definition.getCapabilityCodes()
                        .stream()
                        .map(capabilityService::getByCode)
                        .collect(Collectors.toSet());

        DiffResult<Capability> diff = DiffUtils.diff(
                role.getCapabilities(),
                targetCapabilities,
                Capability::getCode
        );

        if (diff.isEmpty()) {
            return;
        }

        addMissingCapabilities(role, diff.getToAdd());
        removeObsoleteCapabilities(role, diff.getToRemove());
    }

    /**
     * Adds capabilities missing from the persisted role state.
     */
    private void addMissingCapabilities(Role role, List<Capability> capabilitiesToAdd) {

        if (capabilitiesToAdd.isEmpty()) {
            return;
        }

        for (Capability capability : capabilitiesToAdd) {

            roleService.addCapability(
                    RoleName.of(role.getName()),
                    CapabilityCode.of(capability.getCode())
            );
        }
    }

    /**
     * Removes obsolete capabilities that no longer exist
     * in the canonical system role definition.
     */
    private void removeObsoleteCapabilities(Role role, List<Capability> obsoleteCapabilities) {

        if (obsoleteCapabilities.isEmpty()) {
            return;
        }

        for (Capability capability : obsoleteCapabilities) {

            roleService.removeCapability(
                    RoleName.of(role.getName()),
                    CapabilityCode.of(capability.getCode())
            );

        }
    }

}