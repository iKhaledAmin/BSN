package com.khaled_amin.book_social_network.identity.core.model;


import com.khaled_amin.book_social_network.identity.core.resolver.ActorPrincipalResolver;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorSourceResolver;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

import java.util.List;

/**
 * Represents an abstract system actor responsible for performing actions.
 *
 * <p>
 * An {@code Actor} is a core identity abstraction used across the system
 * to model <b>who is performing an operation</b>, regardless of the underlying
 * source (e.g., authenticated user, anonymous request, system process or may be external service).
 * </p>
 *
 * <p>
 * This interface forms the foundation for authorization, policy enforcement,
 * auditing, and cross-cutting concerns where actor context is required.
 * </p>
 *
 * <h3>Conceptual Model</h3>
 * <ul>
 *   <li><b>{@link Actor}</b> → Business identity abstraction (application layer)</li>
 *   <li><b>{@link ActorSource}</b> → Domain entity representation of identity ownership</li>
 *   <li><b>{@link AuthenticatedPrincipal}</b> → SECURITY representation (authentication layer)</li>
 * </ul>
 *
 *
 * <h3>Design Principles</h3>
 * <ul>
 *   <li>Decouples business logic from infrastructure concerns (e.g., Spring SECURITY)</li>
 *   <li>Provides a unified representation for all actor types</li>
 *   <li>Enables cross-cutting concerns (e.g., auditing, authorization)</li>
 *   <li>Supports extensibility (Open/Closed) for future actor types (e.g., services, external systems)</li>
 *   <li>Adding new actor type :</li>
 *   <ul>
 *       <li>1- Create new actor class that extends {@link AbstractActor} </li>
 *       <li>2- Create new domain entity [if needed] that extends {@link ActorSource} </li>
 *       <li>3- Create new actor resolver that implements {@link ActorPrincipalResolver} </li>
 *       <li>3- Create new actor resolver that implements {@link ActorSourceResolver} </li>
 *   </ul>
 * </ul>
 *
 * <h3>Common Actor Types</h3>
 * <ul>
 *   <li>Account actor the authenticated user account (e.g., logged-in user)</li>
 *   <li>Anonymous actor the unauthenticated user (unauthenticated request)</li>
 *   <li>System actor (background jobs, scheduled tasks)</li>
 *   <li>Service actor (inter-service communication)</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Used in policy engines to enforce authorization rules</li>
 *   <li>Propagated through application services to evaluate permissions</li>
 *   <li>Serves as the canonical identity representation for business logic</li>
 * </ul>
 *
 * @see AbstractActor
 * @see ActorIdentity
 * @see ActorType
 * @see ActorPrincipalResolver
 * @see ActorSourceResolver
 */

public interface Actor {

    /**
     * Returns the unique identity of the actor.
     *
     * <p>
     * The identity encapsulates both the actor type and its unique identifier.
     * It is the primary reference used for comparisons and cross-system operations.
     * </p>
     *
     * @return non-null {@link ActorIdentity}
     */
    ActorIdentity getActorIdentity();

    /**
     * Returns the type of the actor.
     *
     * <p>
     * The type determines the category of the actor (e.g., ACCOUNT, SYSTEM, ANONYMOUS),
     * and is typically used in policy evaluation and conditional logic.
     * </p>
     *
     * @return non-null {@link ActorType}
     */
    ActorType getType();


    /**
     * Checks whether the actor has a specific authority [ role or scope ].
     *
     * <p>
     * Role semantics depend on the concrete actor implementation.
     * For example, an account actor may delegate this to its assigned roles,
     * while a system actor may always return {@code true} or {@code false}
     * while client actor may delegate this to its assigned scopes.
     * </p>
     *
     * @param authority {@link String} the authority to check
     * @return {@code true} if the actor has the specified authority, otherwise {@code false}
     */
    boolean hasAuthority(String authority);


    /**
     * Checks whether the actor has at least one of the specified authorities [ roles or scopes ].
     *
     * <p>
     * This is a convenience method for evaluating multiple roles or scopes in a single call.
     * </p>
     *
     * @param authorities {@link List} of authorities names
     * @return {@code true} if the actor has any of the specified authorities, otherwise {@code false}
     */
    boolean hasAnyAuthority(String... authorities);


    // future
//    boolean hasPermission(String permission);
//    boolean hasAnyPermission(String... permissions);


    /**
     * Compares this actor with another identity for equivalence.
     *
     * <p>
     * Two actors are considered the same if both their type and identifier match.
     * </p>
     *
     * @param other {@link ActorIdentity} the identity to compare against
     * @return {@code true} if both identities represent the same actor, otherwise {@code false}
     */
    boolean sameAs(ActorIdentity other);

}
