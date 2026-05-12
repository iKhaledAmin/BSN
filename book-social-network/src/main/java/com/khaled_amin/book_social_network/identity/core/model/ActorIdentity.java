package com.khaled_amin.book_social_network.identity.core.model;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Core identity model representing the unique identity of an {@link Actor}.
 *
 * <p>
 * {@code ActorIdentity} combines:
 * </p>
 * <ul>
 *     <li>{@link ActorType} → defines the actor category</li>
 *     <li>{@link ActorCode} → globally unique business identity</li>
 * </ul>
 *
 * <p>
 * Together they form a stable, globally distinguishable identity
 * across the entire system.
 * </p>
 *
 * <h3>Purpose</h3>
 * <p>
 * This abstraction acts as the unified identity contract used throughout:
 * </p>
 * <ul>
 *     <li>Authorization and policy evaluation</li>
 *     <li>Audit logging and traceability</li>
 *     <li>Ownership modeling</li>
 *     <li>Cross-module communication</li>
 *     <li>Security and actor resolution</li>
 *     <li>Event and transaction attribution</li>
 * </ul>
 *
 * <h3>Identity Model</h3>
 * <p>
 * {@code ActorIdentity} intentionally represents a business/security identity
 * rather than a database primary key.
 * </p>
 *
 * <p>
 * This distinction is critical because:
 * </p>
 * <ul>
 *     <li>Database IDs are persistence concerns</li>
 *     <li>Actor identities are system-wide identity concerns</li>
 * </ul>
 *
 * <p>
 * Therefore, {@link ActorCode} is designed to be:
 * </p>
 * <ul>
 *     <li>Stable</li>
 *     <li>Globally unique</li>
 *     <li>Safe for external exposure</li>
 *     <li>Portable across modules/services</li>
 *     <li>Independent from database implementation details</li>
 * </ul>
 *
 * <h3>Examples</h3>
 * <pre>{@code
 * ACCOUNT   -> acc_f83a91
 * CLIENT    -> cli_91ab22
 * SYSTEM    -> system
 * ANONYMOUS -> anon_session_xxx
 * }</pre>
 *
 * <h3>Persistence Design</h3>
 * <p>
 * This class is modeled as a JPA {@link Embeddable}
 * because it is frequently embedded into domain entities
 * to represent ownership and actor references.
 * </p>
 *

 * <h3>Implementation Example</h3>
 * <p>
 * {@code ActorIdentity} is commonly embedded into domain entities
 * to model ownership, attribution, or actor-based relationships
 * without creating tight coupling to a concrete actor entity.
 * </p>
 *
 * <p>
 * This allows the domain model to reference the abstract actor system
 * through a stable business identity representation.
 * </p>
 *
 * <pre>{@code
 * @Entity
 * @Table(name = "stock_items")
 * public class StockItem extends AuditableEntity {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     @Column(name = "stock_item_id")
 *     private Long id;
 *
 *     @Embedded
 *     @AttributeOverrides({
 *             @AttributeOverride(
 *                     name = "actorType",
 *                     column = @Column(
 *                             name = "owner_actor_type",
 *                             nullable = false,
 *                             updatable = false
 *                     )
 *             ),
 *             @AttributeOverride(
 *                     name = "actorCode.value",
 *                     column = @Column(
 *                             name = "owner_actor_code",
 *                             nullable = false,
 *                             updatable = false
 *                     )
 *             )
 *     })
 *     private ActorIdentity ownerIdentity;
 * }
 * }</pre>
 *
 * <p>
 * In this example:
 * </p>
 * <ul>
 *     <li>{@code ownerIdentity} references the actor who owns the stock item</li>
 *     <li>The relationship is modeled through business identity rather than a direct entity association</li>
 *     <li>The domain remains decoupled from concrete actor implementations</li>
 *     <li>The ownership model remains stable across modules and bounded contexts</li>
 * </ul>

 *
 * <h3>Design Characteristics</h3>
 * <ul>
 *     <li>Value object semantics</li>
 *     <li>Equality based entirely on state</li>
 *     <li>Self-validating upon creation</li>
 *     <li>Semantically immutable</li>
 * </ul>
 *
 * <h3>Important Notes</h3>
 * <ul>
 *     <li>This is NOT a persistence entity</li>
 *     <li>This class does NOT represent authentication state</li>
 *     <li>This class does NOT contain authorization logic</li>
 *     <li>This class should never expose public setters</li>
 * </ul>
 *
 * @see Actor
 * @see ActorType
 * @see ActorCode
 * @see AbstractActor
 */
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActorIdentity {

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", nullable = false ,updatable = false)
    private ActorType actorType;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "actor_code",
                    nullable = false,
                    updatable = false,
                    comment = "Stable globally unique business identity"
            )
    )
    private ActorCode actorCode;


    private ActorIdentity(ActorType actorType, ActorCode actorCode) {
        validate(actorType, actorCode);

        this.actorType = actorType;
        this.actorCode = actorCode;
    }

    /**
     * Factory method for creating an {@link ActorIdentity}.
     *
     * @param actorType {@link ActorType} actor category
     * @param actorCode {@link ActorCode} stable actor business identity
     * @return {@link ActorIdentity} validated actor identity
     */
    public static ActorIdentity of(ActorType actorType, ActorCode actorCode) {
        return new ActorIdentity(actorType, actorCode);
    }


    /**
     * Determines whether this identity represents the same actor
     * as another identity.
     *
     * @param otherIdentity {@link ActorIdentity} identity to compare against
     * @return {@code true} if both identities match
     */
    public boolean sameAs(ActorIdentity otherIdentity) {

        if (otherIdentity == null) {
            return false;
        }

        return Objects.equals(this.actorType, otherIdentity.actorType)
                && Objects.equals(this.actorCode, otherIdentity.actorCode);
    }

    private void validate(ActorType actorType, ActorCode actorCode) {

        if (actorType == null) {
            throw IdentityException.invalidIdentity()
                    .withDetail("reason", "Actor type must not be null");
        }

        if (actorCode == null) {
            throw IdentityException.invalidIdentity()
                    .withDetail("reason", "Actor code must not be null");
        }
    }

}