package com.khaled_amin.book_social_network.identity.core.model;

import com.khaled_amin.book_social_network.core.policy.core.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.core.generator.ActorCodeGenerator;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorSourceResolver;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

/**
 * Represents a domain object capable of participating in the system
 * as an identifiable {@link Actor}.
 *
 * <p>
 * {@code ActorSource} is the foundational identity contract that bridges
 * domain entities with the system-wide actor model.
 * </p>
 *
 * <p>
 * Implementing this interface means the object owns a stable business identity
 * represented by:
 * </p>
 * <ul>
 *     <li>{@link ActorType} → defines the actor category</li>
 *     <li>{@link ActorCode} → globally unique business identity</li>
 * </ul>
 *
 * <p>
 * Together, these values form an {@link ActorIdentity},
 * which becomes the canonical identity representation used across:
 * </p>
 * <ul>
 *     <li>Authorization and policy evaluation</li>
 *     <li>Audit logging and traceability</li>
 *     <li>Security and authentication flows</li>
 *     <li>Cross-module identity propagation</li>
 *     <li>Ownership and attribution models</li>
 * </ul>
 *
 * <h3>Architectural Role</h3>
 * <ul>
 *     <li><b>{@link ActorSource}</b> → domain identity owner</li>
 *     <li><b>{@link AuthenticatedPrincipal}</b> → authenticated security representation</li>
 *     <li><b>{@link Actor}</b> → lightweight business actor abstraction</li>
 * </ul>
 *
 * <h3>Identity Model</h3>
 * <p>
 * This interface intentionally separates:
 * </p>
 * <ul>
 *     <li><b>Persistence identity</b> (database primary keys)</li>
 *     <li><b>Business/security identity</b> ({@link ActorIdentity})</li>
 * </ul>
 *
 * <p>
 * Therefore, implementations should expose a stable {@link ActorCode}
 * instead of relying on database IDs.
 * </p>
 *
 * <p>
 * Actor codes should be generated through
 * {@link ActorCodeGenerator#generate(ActorType)}
 * to guarantee consistency, uniqueness, and adherence
 * to the system identity strategy.
 * </p>
 *
 * <h3>Design Characteristics</h3>
 * <ul>
 *     <li>Composition-based identity model</li>
 *     <li>Technology-independent business identity</li>
 *     <li>Supports heterogeneous actor systems</li>
 *     <li>Safe for distributed and cross-module communication</li>
 *     <li>JPA-friendly and inheritance-safe</li>
 * </ul>
 *
 * <h3>Resolution Flow</h3>
 * <p>
 * Implementations can be resolved into an {@link Actor}
 * through the {@link ActorSourceResolver} infrastructure.
 * </p>
 *
 * <p>
 * Resolution typically relies on:
 * </p>
 * <ul>
 *     <li>{@link #getActorType()}</li>
 *     <li>{@link #getActorCode()}</li>
 *     <li>A registered resolver for the corresponding {@link ActorType}</li>
 * </ul>
 *
 * <h3>When to Implement</h3>
 * <p>
 * This interface should only be implemented by domain entities that:
 * </p>
 * <ul>
 *     <li>Represent a real system identity</li>
 *     <li>Participate in authorization decisions</li>
 *     <li>Need actor-level traceability or auditing</li>
 *     <li>Must be resolved into an {@link Actor}</li>
 * </ul>
 *
 * <h3>Typical Examples</h3>
 * <ul>
 *     <li>Account</li>
 *     <li>Client</li>
 *     <li>Service integrations</li>
 *     <li>Future machine or API identities</li>
 * </ul>
 *
 * <h3>When NOT to Implement</h3>
 * <ul>
 *     <li>DTOs or request models</li>
 *     <li>Value objects</li>
 *     <li>Helper or utility classes</li>
 *     <li>Entities without identity semantics</li>
 *     <li>Objects that should never participate in authorization</li>
 * </ul>
 *
 * <h3>Security Considerations</h3>
 * <p>
 * Implementing this interface is a security-sensitive architectural decision.
 * Any implementing type becomes part of the system identity boundary and may:
 * </p>
 * <ul>
 *     <li>Participate in policy evaluation</li>
 *     <li>Influence authorization decisions</li>
 *     <li>Appear in audit trails and security contexts</li>
 * </ul>
 *
 * <p>
 * Incorrect implementation may introduce:
 * </p>
 * <ul>
 *     <li>Privilege escalation vulnerabilities</li>
 *     <li>Broken authorization mappings</li>
 *     <li>Identity spoofing risks</li>
 * </ul>
 *
 * <p>
 * Therefore, implementations must guarantee:
 * </p>
 * <ul>
 *     <li>Stable and valid actor identities</li>
 *     <li>Correct {@link ActorType} exposure</li>
 *     <li>Globally unique {@link ActorCode} ownership</li>
 * </ul>
 *
 * <h3>Implementation Example</h3>
 * <pre>{@code
 * @Entity
 * public class Account implements ActorSource {
 *
 *     @Embedded
 *     @AttributeOverride(
 *             name = "value",
 *             column = @Column(
 *                     name = "account_code",
 *                     nullable = false,
 *                     updatable = false,
 *                     unique = true,
 *                     comment = "Stable globally unique business identity"
 *             )
 *     )
 *     private ActorCode accountCode;
 *
 *
 *     @Override
 *     public ActorType getActorType() {
 *         return ActorType.ACCOUNT;
 *     }
 *
 *     @Override
 *     public ActorCode getClientCode() {
 *         return accountCode;
 *     }
 * }
 * }</pre>
 *
 * <h3>Related Components</h3>
 * <ul>
 *     <li>{@link Actor}</li>
 *     <li>{@link ActorIdentity}</li>
 *     <li>{@link ActorCode}</li>
 *     <li>{@link ActorType}</li>
 *     <li>{@link ActorCodeGenerator}</li>
 *     <li>{@link ActorSourceResolver}</li>
 *     <li>{@link AbstractPolicy}</li>
 * </ul>
 */
public interface ActorSource {

    /**
     * Returns the actor category represented by this source.
     *
     * <p>
     * The returned type must remain stable throughout
     * the lifecycle of the implementing entity.
     * </p>
     *
     * @return non-null {@link ActorType}
     */
    ActorType getActorType();

    /**
     * Returns the stable globally unique business identity
     * of this actor source.
     *
     * <p>
     * This value must:
     * </p>
     * <ul>
     *     <li>Be globally unique</li>
     *     <li>Remain stable over time</li>
     *     <li>Be independent of database primary keys</li>
     * </ul>
     *
     * @return non-null {@link ActorCode}
     */
    ActorCode getActorCode();

    /**
     * Returns the canonical {@link ActorIdentity}
     * representing this actor source.
     *
     * <p>
     * The default implementation composes:
     * </p>
     * <ul>
     *     <li>{@link #getActorType()}</li>
     *     <li>{@link #getActorCode()}</li>
     * </ul>
     *
     * @return non-null {@link ActorIdentity}
     */
    default ActorIdentity getActorIdentity() {
        return ActorIdentity.of(
                getActorType(),
                getActorCode()
        );
    }
}