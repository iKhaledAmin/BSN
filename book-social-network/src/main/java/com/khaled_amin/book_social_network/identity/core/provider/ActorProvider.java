package com.khaled_amin.book_social_network.identity.core.provider;

import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

/**
 * Primary contract for obtaining the current business identity abstraction ({@link Actor}).
 *
 * <p>
 * This interface provides a unified and consistent way to access the identity of the
 * current caller or to translate external identity representations into a domain-level
 * {@link Actor}.
 * </p>
 *
 * <h3>Core Responsibility</h3>
 * <p>
 * The only responsibility of this contract is to ensure that every part of the system
 * can work with a single, consistent identity model: {@link Actor}.
 * </p>
 *
 * <h3>Supported Input Types</h3>
 * <ul>
 *   <li>Current execution context</li>
 *   <li>Domain identity sources ({@link ActorSource})</li>
 *   <li>SECURITY-layer principals ({@link AuthenticatedPrincipal})</li>
 * </ul>
 *
 * <h3>General Contract Rules</h3>
 * <ul>
 *   <li>All methods must return a non-null {@link Actor}</li>
 *   <li>Inputs must represent a valid and known identity in the system</li>
 *   <li>No method performs silent fallback or default guessing</li>
 *   <li>Identity resolution must always be explicit and deterministic</li>
 * </ul>
 *
 * <h3>Failure Behavior</h3>
 * <ul>
 *   <li>Any unsupported, invalid, or unrecognized identity input results in
 *       {@link TechnicalException}</li>
 *   <li>Such failures indicate system misconfiguration or invalid usage</li>
 *   <li>These errors are considered system-level and must not be exposed directly to end users</li>
 * </ul>
 *
 * <h3>Design Note</h3>
 * <p>
 * This contract exists to isolate business logic from security and infrastructure
 * identity representations.
 * </p>
 *
 * @see Actor
 * @see ActorSource
 * @see AuthenticatedPrincipal
 * @see TechnicalException
 */
public interface ActorProvider {

    /**
     * Returns the {@link Actor} representing the current execution context.
     *
     * <h3>Behavior</h3>
     * <ul>
     *   <li>Authenticated request → returns authenticated actor</li>
     *   <li>Anonymous request → returns anonymous actor</li>
     *   <li>No security context → returns system actor</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>Must never return {@code null}</li>
     *   <li>Must always produce a valid system-known actor type</li>
     * </ul>
     *
     * @return current execution {@link Actor}
     * @throws TechnicalException if the security context is invalid or cannot be interpreted
     */
    Actor getCurrent();

    /**
     * Converts a domain-level identity source into corresponding business abstraction level {@link Actor}.
     *
     * <p>
     * This method is used when identity is derived from a domain object
     * that explicitly represents ownership or actor context.
     * </p>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code source} must not be {@code null}</li>
     *   <li>⚠️ {@code source} must be a real domain object that implements {@link ActorSource} not fake object</li>
     *   <li>⚠️ The source must represent a valid system-recognized identity</li>
     * </ul>
     *
     * @param source domain identity representation
     * @return resolved {@link Actor}
     * @throws TechnicalException if the source cannot be mapped to a valid actor
     */
    Actor getFrom(ActorSource source);

    /**
     * Converts a security-level principal into an {@link Actor}.
     *
     * <p>
     * This method is used when identity originates from authentication mechanisms.
     * </p>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code principal} must not be {@code null}</li>
     *   <li>⚠️ {@code principal} must represent a recognized authentication type</li>
     * </ul>
     *
     * @param principal authenticated security principal
     * @return resolved {@link Actor}
     * @throws TechnicalException if the principal type is unknown or unsupported
     */
    Actor getFrom(AuthenticatedPrincipal principal);

}