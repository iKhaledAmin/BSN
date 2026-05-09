package com.khaled_amin.book_social_network.identity.core.model;


import com.khaled_amin.book_social_network.core.policy.core.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorSourceResolver;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

/**
 * Represents a domain object that can act as a source of {@link Actor}.
 *
 * <p>
 * {@code ActorSource} is a strategic contract that allows a domain entity
 * to expose itself as an {@link Actor} within the system.
 * It acts as a bridge between domain models and the business actor abstraction layer.
 * </p>
 *
 * <h3>Conceptual Model</h3>
 * <ul>
 *   <li><b>{@link ActorSource}</b> → Domain entity representation of identity ownership</li>
 *   <li><b>{@link AuthenticatedPrincipal}</b> → Security representation (authentication layer)</li>
 *   <li><b>{@link Actor}</b> → Business identity abstraction (application layer)</li>
 * </ul>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Enable domain entities to participate in identity-based operations</li>
 *   <li>Allow transformation of domain objects into {@link Actor} via resolvers</li>
 *   <li>Support cross-layer identity propagation (e.g., policies, auditing, security)</li>
 * </ul>
 *
 * <h3>How It Works</h3>
 * <p>
 * Any class implementing this interface can be resolved into an {@link Actor}
 * using the {@link  ActorSourceResolver} mechanism.
 * The resolution process relies on:
 * </p>
 * <ul>
 *   <li>{@link ActorIdentity#getActorType()} to determine the appropriate resolver</li>
 *   <li>A registered {@link  ActorSourceResolver} for that {@link ActorType}</li>
 * </ul>
 *
 * <h3>When to Implement</h3>
 * <p>
 * This interface should ONLY be implemented by domain entities that:
 * </p>
 * <ul>
 *   <li>Represent a real identity in the system (e.g., Account, Client)</li>
 *   <li>Need to be treated as an {@link Actor} in business logic</li>
 *   <li>Participate in authorization, policy evaluation, or auditing</li>
 * </ul>
 *
 * <h3>When NOT to Implement</h3>
 * <ul>
 *   <li>DTOs, commands, or request/response models</li>
 *   <li>Value objects or technical/helper classes</li>
 *   <li>Entities that are not part of the identity model</li>
 *   <li>Any class where exposing identity could lead to security leaks</li>
 * </ul>
 *
 * <h3>⚠️ Important Design Warning</h3>
 * <p>
 * Implementing {@code ActorSource} is a <b>security-sensitive decision</b>.
 * By doing so, the entity becomes eligible to be resolved into an {@link Actor},
 * which means:
 * </p>
 * <ul>
 *   <li>It can be used in authorization and policy evaluation</li>
 *   <li>It becomes part of the system's identity boundary</li>
 *   <li>It may influence access control decisions</li>
 * </ul>
 *
 * <p>
 * Incorrect usage may result in:
 * </p>
 * <ul>
 *   <li>Privilege escalation vulnerabilities</li>
 *   <li>Invalid identity mappings</li>
 *   <li>Broken authorization rules</li>
 * </ul>
 *
 * <p>
 * Therefore, this interface should only be implemented when:
 * </p>
 * <ul>
 *   <li>The entity has a well-defined and stable identity</li>
 *   <li>The {@link ActorType} mapping is explicit and correct</li>
 *   <li>A corresponding {@code ActorSourceResolver} is properly implemented</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * @Entity
 * public class Account implements ActorSource {
 *
 *     @Override
 *     public ActorIdentity getActorIdentity() {
 *         return ActorIdentity.of(ActorType.ACCOUNT, id.toString());
 *     }
 * }
 * }</pre>
 *
 * <h3>Related Components</h3>
 * <ul>
 *   <li>{@link Actor}</li>
 *   <li>{@link ActorIdentity}</li>
 *   <li>{@link ActorType}</li>
 *   <li>{@link ActorSourceResolver}</li>
 *   <li>{@link AbstractPolicy}</li>
 * </ul>
 */
public interface ActorSource {

    /**
     * Returns the {@link ActorIdentity} representing this source.
     *
     * <p>
     * The returned identity must be:
     * </p>
     * <ul>
     *   <li>Non-null</li>
     *   <li>Consistent with the actual entity identity</li>
     *   <li>Stable across the lifecycle of the entity</li>
     * </ul>
     *
     * @return non-null {@link ActorIdentity}
     */
    ActorIdentity getActorIdentity();
}