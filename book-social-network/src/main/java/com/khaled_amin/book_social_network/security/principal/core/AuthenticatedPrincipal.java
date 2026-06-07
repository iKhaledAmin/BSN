package com.khaled_amin.book_social_network.security.principal.core;

import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * SECURITY-layer identity contract representing an authenticated system principal.
 *
 * <p>
 * This interface is the canonical representation of an authenticated entity within
 * the security layer. It acts as the bridge input for transforming authentication
 * information into a business-level {@link Actor}.
 * </p>
 *
 * <h3>Conceptual Model</h3>
 * <ul>
 *   <li><b>{@link AuthenticatedPrincipal}</b> → SECURITY representation (authentication layer)</li>
 *   <li><b>{@link Actor}</b> → Business identity abstraction (application layer)</li>
 *   <li><b>{@link ActorSource}</b> → Domain entity representation of identity ownership</li>
 * </ul>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Standardize how authenticated identities are represented in the security layer</li>
 *   <li>Provide a unified contract for multiple authentication sources (accounts, clients, etc.)</li>
 *   <li>Enable safe and consistent translation into domain-level {@link Actor}</li>
 * </ul>
 *
 * <h3>Implementation Contract</h3>
 * <p>Every implementation MUST satisfy the following rules:</p>
 * <ul>
 *   <li>{@link #getSubject()} must return a stable unique identifier for the principal</li>
 *   <li>{@link #getActorType()} must correctly identify the originating identity type</li>
 *   <li>{@link #getAuthorities()} must represent granted permissions in Spring-compatible format</li>
 *   <li>{@link #isActive()} and {@link #isLocked()} must reflect the current security state</li>
 *   <li>Implementations must be immutable or effectively immutable</li>
 * </ul>
 *
 * <h3>Identity Semantics</h3>
 * <ul>
 *   <li><b>Subject</b>: logical identity key (username, clientId, etc.)</li>
 *   <li><b>ActorType</b>: classification of identity source (ACCOUNT, CLIENT, SYSTEM, etc.)</li>
 *   <li><b>Authorities</b>: granted permissions used by authorization layer</li>
 * </ul>
 *
 * <h3>SECURITY Constraints</h3>
 * <ul>
 *   <li>Implementations must be created only from trusted authentication sources</li>
 *   <li>No mutable identity fields after construction</li>
 *   <li>Must not expose sensitive credentials (e.g., raw passwords)</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Invalid or inconsistent implementations indicate system misconfiguration</li>
 *   <li>This interface does not define runtime validation exceptions</li>
 *   <li>Consumers must treat principal data as trusted within authenticated context</li>
 * </ul>
 *
 * <h3>Design Note</h3>
 * <p>
 * This contract is intentionally minimal and framework-agnostic to avoid coupling
 * the business identity model to Spring SECURITY internals.
 * </p>
 */
public interface AuthenticatedPrincipal {


    /**
     * Returns the unique identity of this principal.
     *
     * <p>
     * This value represents the stable identifier used during authentication
     * (e.g., username, clientId, or external identity key).
     * </p>
     *
     * @return {@link String} non-null unique identity string
     */
    String getSubject();

    /**
     * Return the actor code the represent the business identifier.
     * @return {@link ActorCode} non-null actor code
     */
    ActorCode getActorCode();


    /**
     * Returns the type of actor this principal represents.
     *
     * <p>
     * This value is used to determine how the principal is translated into a
     * business-level {@link Actor}.
     * </p>
     *
     * @ type {@link ActorType}return non-null actor type
     */
    ActorType getActorType();



    /**
     * Indicates whether the principal is currently active.
     *
     * @return {@code true} if enabled and allowed to authenticate, otherwise {@code false}
     */
    boolean isActive();

    /**
     * Indicates whether the principal is locked or restricted.
     *
     * @return {@code true} if access is restricted, otherwise {@code false}
     */
    boolean isLocked();

    /**
     * Returns the authorities granted to this principal.
     *
     * <p>
     * Authorities are used by the authorization layer to enforce access control.
     * </p>
     *
     * @return collection of granted authorities (never null)
     */
    Collection<? extends GrantedAuthority> getAuthorities();



    /**
     * Validates whether this principal supports a given token subject.
     *
     * <p>
     * This is typically used during token validation to ensure consistency
     * between persisted identity and token identity.
     * </p>
     *
     * @param subject {@link String} token subject to verify
     * @return {@code true} if the subject matches this principal
     */
    default boolean supportsToken(String subject) {
        return getSubject().equals(subject);
    }


}