package com.khaled_amin.book_social_network.identity.capability.domain.registry;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central in-memory registry of all platform capability definitions.
 *
 * <p>
 * The registry aggregates capability definitions from all
 * discovered {@link CapabilityProvider} implementations
 * during application startup.
 * </p>
 *
 * <p>
 * It acts as the canonical source of truth for:
 * </p>
 *
 * <ul>
 *     <li>Capability discovery</li>
 *     <li>Bootstrap synchronization</li>
 *     <li>Authorization metadata</li>
 *     <li>Capability validation</li>
 * </ul>
 *
 * <p>
 * The registry guarantees global uniqueness of capability codes.
 * Duplicate registrations cause application startup failure.
 * </p>
 *
 * <p>
 * All registered capabilities are immutable after initialization.
 * </p>
 */
@Component
public class CapabilityRegistry {

    private final Map<String, CapabilityDefinition> capabilities;

    /**
     * Creates the capability registry from all discovered providers.
     *
     * <p>
     * During initialization:
     * </p>
     *
     * <ul>
     *     <li>All providers are scanned</li>
     *     <li>All capabilities are aggregated</li>
     *     <li>Duplicate codes are validated</li>
     *     <li>An immutable registry map is created</li>
     * </ul>
     *
     * @param providers discovered capability providers
     *
     * @throws CapabilityDomainException
     * if duplicate capability codes are detected
     */
    public CapabilityRegistry(List<CapabilityProvider> providers) {

        Map<String, CapabilityDefinition> map = new HashMap<>();

        for (CapabilityProvider provider : providers) {

            for (CapabilityDefinition capability : provider.getCapabilities()) {

                String code = capability.getCode().value();

                if (map.containsKey(code)) {
                    throw CapabilityDomainException
                            .invalidCapability()
                            .withDetail("reason", "Duplicate capabilities code detected")
                            .withDetail("capabilityCode", code);
                }

                map.put(code, capability);
            }
        }

        this.capabilities = Map.copyOf(map);
    }

    /**
     * Returns a capability definition by its immutable code.
     *
     * @param code capability code
     *
     * @return matching capability definition
     *
     * @throws CapabilityDomainException
     * if no capability exists for the given code
     */
    public CapabilityDefinition getByCode(String code) {

        CapabilityDefinition capability = capabilities.get(code);

        if (capability == null) {
            throw CapabilityDomainException
                    .invalidCapability()
                    .withDetail("reason", "Capability not found")
                    .withDetail("capabilityCode", code);
        }

        return capability;
    }

    /**
     * Returns all registered capability definitions.
     *
     * @return immutable collection of capabilities
     */
    public Collection<CapabilityDefinition> getAll() {
        return capabilities.values();
    }
}