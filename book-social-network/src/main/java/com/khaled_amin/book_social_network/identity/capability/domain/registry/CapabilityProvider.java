package com.khaled_amin.book_social_network.identity.capability.domain.registry;

import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;

import java.util.Collection;

/**
 * Contract for exposing capability definitions to the registry system.
 *
 * <p>
 * Each bounded domain module exposes one or more implementations
 * of this interface to publish its authorization capabilities.
 * </p>
 *
 * <p>
 * These providers are automatically discovered by Spring and aggregated
 * by the {@link CapabilityRegistry} during application definition.
 * </p>
 *
 * <p>
 * Typical implementations are enum-based capability registries
 * representing a domain module (e.g., ROLE, ACCOUNT).
 * </p>
 *
 * <h2>Example</h2>
 *
 * <pre>{@code
 * @Component
 * public class RoleCapabilityProvider implements CapabilityProvider {
 *
 *     @Override
 *     public Collection<RoleCapability> getCapabilityCodes() {
 *         return List.of(RoleCapability.values());
 *     }
 * }
 * }</pre>
 */
public interface CapabilityProvider {

    /**
     * Returns all capability definitions exposed by this provider.
     *
     * @return immutable or read-only collection of capability definitions
     */
    Collection<? extends CapabilityDefinition> getCapabilities();
}