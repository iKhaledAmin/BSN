package com.khaled_amin.book_social_network.identity.core.model;


import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;

/**
 * Immutable value object representing the unique identity of an {@link Actor}.
 *
 * <p>
 * Combines the actor type with a unique identifier to form a globally
 * distinguishable identity across the system.
 * </p>
 *
 * <p>
 * This abstraction is used extensively in:
 * </p>
 * <ul>
 *   <li>Authorization and policy evaluation</li>
 *   <li>Audit logging and traceability</li>
 *   <li>Cross-module communication (e.g., verification, tokens)</li>
 * </ul>
 *
 * <h3>Design Characteristics</h3>
 * <ul>
 *   <li>Immutable and thread-safe</li>
 *   <li>Self-validating upon construction</li>
 *   <li>Equality defined by both type and identifier</li>
 * </ul>
 *
 * <h3>ActorSource Semantics</h3>
 * <ul>
 *   <li>{@code type} {@link ActorType} defines the actor category</li>
 *   <li>{@code id} {@link String} uniquely identifies the actor within that category</li>
 * </ul>
 *
 * @param type {@link ActorType} the actor type (must not be null)
 * @param id {@link String} the unique identifier (must not be null)
 *
 * @see Actor
 * @see ActorType
 * @see AbstractActor
 */

public record ActorIdentity(ActorType type, String id) {

    /**
     * Canonical constructor with validation.
     */
    public ActorIdentity{
        validate(type,id);
    }



    /**
     * Returns the actor id.
     *
     * @return {@link String} the unique identifier
     */
    public String getActorId() {
        return id;
    }

    /**
     * Returns the actor type.
     *
     * @return {@link ActorType} the actor type
     */
    public ActorType getActorType() {
        return type;
    }

    /**
     * Factory method for creating an {@link ActorIdentity}.
     *
     * @param type {@link ActorType}the actor type
     * @param id {@link String} the unique identifier
     * @return new {@link ActorIdentity} instance
     */
    public static ActorIdentity of(ActorType type, String id) {
        return new ActorIdentity(type,id);
    }


    /**
     * Compares this identity with another for equality.
     *
     * <p>
     * Two identities are considered equal if both their type and identifier match.
     * </p>
     *
     * @param otherIdentity {@link ActorIdentity} the identity to compare against
     * @return {@code true} if both identities represent the same actor, otherwise {@code false}
     */
    public boolean sameAs(ActorIdentity otherIdentity) {
        if (otherIdentity == null) return false;
        return this.type == otherIdentity.type && this.id.equals(otherIdentity.id);
    }

    private void validate(ActorType type, String id){
        if (type == null )
            throw IdentityException.invalidIdentity()
                    .withDetail("reason","Actor identity type must not be null");

        if (id == null )
            throw IdentityException.invalidIdentity()
                    .withDetail("reason","Actor identity id must not be null");
    }
}