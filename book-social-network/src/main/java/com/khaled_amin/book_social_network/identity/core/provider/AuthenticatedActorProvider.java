package com.khaled_amin.book_social_network.identity.core.provider;

import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.AnonymousActor;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.SystemActor;
import com.khaled_amin.book_social_network.identity.core.registry.ActorPrincipalResolverRegistry;

/**
 * Provides access to the current authenticated {@link Actor}.
 *
 * <p>
 * This interface represents the primary entry point for retrieving the
 * actor associated with the current execution context.
 * It acts as a bridge between the security layer and the business-level
 * identity abstraction.
 * </p>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Expose a consistent way to access the current {@link Actor}</li>
 *   <li>Decouple application logic from security framework clientDetails</li>
 *   <li>Ensure all business operations operate on a unified identity model</li>
 * </ul>
 *
 * <h3>Implementation Contract</h3>
 * <ul>
 *   <li>Must retrieve the current authentication context from the underlying security mechanism</li>
 *   <li>Must determine the appropriate {@link Actor} representation for the current context</li>
 *   <li>Must delegate principal-to-actor transformation to {@link ActorPrincipalResolverRegistry}</li>
 *   <li>Must handle different execution contexts (authenticated, anonymous, system)</li>
 *   <li>Must never return {@code null}</li>
 * </ul>
 *
 * <h3>Behavioral Expectations</h3>
 * <ul>
 *   <li>If a valid authenticated principal exists → use {@link ActorPrincipalResolverRegistry} to
 *   resolve and return corresponding {@link Actor} type</li>
 *   <li>If the context represents an anonymous interaction → return an anonymous actor {@link AnonymousActor} </li>
 *   <li>If no authentication context exists → return a system-level actor {@link SystemActor} </li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Failures during resolution (e.g., missing resolver, type mismatch)
 *       must propagate as {@link TechnicalException}</li>
 *   <li>Such failures indicate configuration or developer mistakes, not user errors</li>
 *   <li>If the authentication or principal type is not recognized or unsupported,
 *       the implementation must throw {@link TechnicalException}
 *       and must not fall back to any default actor</li>
 * </ul>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *   <li>This interface belongs to the identity core layer</li>
 *   <li>Implementations live in the security/infrastructure layer</li>
 *   <li>Prevents leakage of security-specific concepts into business logic</li>
 * </ul>
 *
 * @see Actor
 * @see ActorPrincipalResolverRegistry
 */

public interface AuthenticatedActorProvider {

    /**
     * Returns the {@link Actor} representing the current execution context.
     *
     * <p>
     * The returned actor abstracts the caller identity into a business-level construct
     * that can be safely used across application and domain layers.
     * </p>
     *
     * @return non-null {@link Actor}
     * @throws TechnicalException If the authentication or principal type is not recognized or unsupported
     */
    Actor getCurrentActor();
}