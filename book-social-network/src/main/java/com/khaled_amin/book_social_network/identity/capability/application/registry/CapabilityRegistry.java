package com.khaled_amin.book_social_network.identity.capability.application.registry;

import com.khaled_amin.book_social_network.identity.capability.application.provider.CapabilityProvider;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityTechnicalException;
import org.springframework.stereotype.Component;

import java.util.*;

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
     * @throws CapabilityTechnicalException
     * if duplicate capability codes are detected
     */
    public CapabilityRegistry(List<CapabilityProvider> providers) {

        Map<String, CapabilityDefinition> map = new HashMap<>();

        for (CapabilityProvider provider : providers) {

            if (provider == null) {
                throw CapabilityTechnicalException.nullProvider();
            }

            Set<? extends CapabilityDefinition> definitions = provider.getCapabilities();

            if (definitions == null) {
                throw CapabilityTechnicalException.nullDefinition();
            }

            for (CapabilityDefinition definition : definitions) {

                if (definition == null) {
                    throw CapabilityTechnicalException.nullDefinition();
                }

                if (definition.getCode() == null) {
                    throw CapabilityTechnicalException.nullDefinition();
                }

                String code = definition.getCode().value();

                if (map.containsKey(code)) {
                    throw CapabilityTechnicalException.duplicateCode()
                            .withDebugDetails("capabilityCode", code);
                }

                map.put(code, definition);
            }
        }

        this.capabilities = Map.copyOf(map);
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