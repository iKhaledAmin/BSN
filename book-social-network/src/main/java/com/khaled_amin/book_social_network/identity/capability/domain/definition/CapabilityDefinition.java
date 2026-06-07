package com.khaled_amin.book_social_network.identity.capability.domain.definition;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.value.*;

/**
 * Canonical immutable definition of a system capability.
 *
 * <p>
 * A capability represents a single atomic authorization unit
 * within the platform.
 * </p>
 *
 * <p>
 * Each capability defines:
 * </p>
 *
 * <ul>
 *     <li>
 *         A stable internal identifier ({@link CapabilityCode})
 *     </li>
 *     <li>
 *         The protected resource ({@link CapabilityResource})
 *     </li>
 *     <li>
 *         The permitted action ({@link CapabilityAction})
 *     </li>
 *     <li>
 *         Human-readable metadata for administration interfaces
 *     </li>
 *     <li>
 *         The logical module to which the capability belongs
 *     </li>
 * </ul>
 *
 * <p>
 * Capability definitions are immutable and are typically implemented
 * as enums inside bounded domain modules.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * ROLE_CREATE
 * STOCK_ITEM_UPDATE
 * ACCOUNT_RESET_PASSWORD
 * </pre>
 *
 * <p>
 * These definitions are registered during application definition
 * and synchronized with the persistence layer.
 * </p>
 */
public interface CapabilityDefinition {

    /**
     * Returns the immutable internal capability identifier.
     *
     * <p>
     * This identifier is globally unique across the platform
     * and is used for persistence, synchronization,
     * permission generation, and authorization decisions.
     * </p>
     *
     * <p>
     * Example:
     * </p>
     *
     * <pre>
     * ROLE_CREATE
     * STOCK_ITEM_UPDATE
     * </pre>
     *
     * @return immutable capability code
     */
    CapabilityCode getCode();

    /**
     * Returns the protected resource targeted by this capability.
     *
     * <p>
     * The resource represents the domain object or aggregate
     * on which authorization is applied.
     * </p>
     *
     * <p>
     * Examples:
     * </p>
     *
     * <pre>
     * role
     * stock_item
     * account
     * </pre>
     *
     * @return protected resource identifier
     */
    CapabilityResource getResource();

    /**
     * Returns the authorized action associated with this capability.
     *
     * <p>
     * The action represents the operation that may be performed
     * on the protected resource.
     * </p>
     *
     * <p>
     * Examples:
     * </p>
     *
     * <pre>
     * create
     * read
     * update
     * delete
     * approve
     * </pre>
     *
     * @return authorized action identifier
     */
    CapabilityAction getAction();

    /**
     * Returns the human-readable capability name.
     *
     * <p>
     * This value is intended for administrative dashboards,
     * API responses, and management interfaces.
     * </p>
     *
     * <p>
     * Unlike capability codes, names may evolve over time
     * without affecting authorization semantics.
     * </p>
     *
     * @return display name
     */
    CapabilityName getName();

    /**
     * Returns the human-readable capability description.
     *
     * <p>
     * Descriptions provide additional context regarding
     * the purpose and authorization semantics of the capability.
     * </p>
     *
     * @return capability description
     */
    CapabilityDescription getDescription();

    /**
     * Returns the logical domain module that owns this capability.
     *
     * <p>
     * Modules are used for organizational grouping,
     * filtering, administration, and definition synchronization.
     * </p>
     *
     * <p>
     * Examples:
     * </p>
     *
     * <pre>
     * ROLE
     * ACCOUNT
     * AUTHENTICATION
     * </pre>
     *
     * @return owning capability module
     */
    SystemDomain getModule();



    /**
     * Determined if this capability assignable or only the system itself can assign or revoke it.
     *
     *
     * @return true if it assignable otherwise return false
     */
    boolean isSystemManaged();
}