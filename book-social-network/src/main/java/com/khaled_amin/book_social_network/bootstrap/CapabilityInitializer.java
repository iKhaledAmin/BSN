package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.registry.CapabilityRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(InitializerOrder.CAPABILITY)
@Component
@RequiredArgsConstructor
public class CapabilityInitializer implements CommandLineRunner {
    private final CapabilityRegistry capabilityRegistry;
    private final CapabilityService capabilityService;

    @Override
    @Transactional
    public void run(String... args) {

        for (CapabilityDefinition definition : capabilityRegistry.getAll()) {

            if (!capabilityService.existsByCode(definition.getCode())) {
                capabilityService.create(definition);
            }

        }

    }
}
