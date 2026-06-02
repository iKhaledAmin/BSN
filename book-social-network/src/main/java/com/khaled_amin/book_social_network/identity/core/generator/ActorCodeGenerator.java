package com.khaled_amin.book_social_network.identity.core.generator;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityTechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

/**
 * Strategy contract responsible for generating stable
 * globally unique {@link ActorCode} values.
 *
 * <p>
 * {@code ActorCodeGenerator} centralizes the system-wide
 * actor identity generation mechanism and guarantees that
 * generated actor codes follow the application's identity rules.
 * </p>
 *
 * <h3>Purpose</h3>
 * <ul>
 *     <li>Generate unique actor business identities</li>
 *     <li>Decouple identity generation from domain entities</li>
 *     <li>Ensure consistent identity formatting across actor types</li>
 *     <li>Support heterogeneous actor systems</li>
 * </ul>
 *
 * <h3>Generation Rules</h3>
 * <p>
 * Implementations are expected to:
 * </p>
 * <ul>
 *     <li>Generate globally unique values</li>
 *     <li>Apply actor-type-specific prefixes</li>
 *     <li>Respect the identity conventions of the system</li>
 *     <li>Produce stable external-safe identifiers</li>
 * </ul>
 *
 * <h3>Typical Examples</h3>
 * <pre>{@code
 * ACC_01JV8K8Y7D8M4XQF2T9W7P3A1C
 * CLI_01JV8K91N5AZR6C2Q8E4M7B9X
 * SYS_INTERNAL
 * ANO_SESSION_XYZ123
 * }</pre>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *     <li>This interface belongs to the identity core</li>
 *     <li>Domain entities should never generate actor codes directly</li>
 *     <li>Identity generation must remain centralized</li>
 *     <li>Implementations may use ULID, UUID, Snowflake, or custom strategies</li>
 * </ul>
 *
 * <h3>Common Implementations</h3>
 * <ul>
 *     <li>ULID-based generators</li>
 *     <li>UUID-based generators</li>
 *     <li>Distributed ID generators</li>
 *     <li>Technical actor static generators</li>
 * </ul>
 *
 * @see ActorCode
 * @see ActorType
 */
public interface ActorCodeGenerator {

    /**
     * Generates a new globally unique {@link ActorCode}
     * for the given {@link ActorType}.
     *
     * <p>
     * The generated code must conform to the identity
     * generation rules associated with the provided actor type.
     * </p>
     *
     * @param actorType {@link ActorType} target actor type
     * @return generated non-null {@link ActorCode}
     * @throws IdentityTechnicalException if the generation fails
     */
    ActorCode generate(ActorType actorType);
}