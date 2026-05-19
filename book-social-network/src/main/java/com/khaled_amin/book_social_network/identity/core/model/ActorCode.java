package com.khaled_amin.book_social_network.identity.core.model;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Immutable value object representing the globally unique external identifier
 * of an {@link Actor}.
 *
 * <p>
 * {@code ActorCode} is the canonical identity reference used across the system
 * to identify actors independently of database primary keys.
 * </p>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Provide a stable identity across bounded contexts</li>
 *   <li>Decouple business identity from database implementation details</li>
 *   <li>Support heterogeneous actor sources (ACCOUNT, CLIENT, SYSTEM, etc.)</li>
 *   <li>Enable safe external exposure of actor references</li>
 * </ul>
 *
 * <h3>Design Characteristics</h3>
 * <ul>
 *   <li>Immutable value object</li>
 *   <li>Embeddable in JPA entities</li>
 *   <li>Self-validating</li>
 *   <li>Globally unique</li>
 *   <li>Database-independent</li>
 * </ul>
 *
 * <h3>Examples</h3>
 * <pre>
 * ACC_01JTX9Y8G7M4K1A2F3D4E5H6J7
 * CLI_01JTXA6P8D9F2S4K5M7N8Q1W2E
 * SYS_INTERNAL
 * ANON_SESSION_ABC123
 * </pre>
 *
 * <h3>Validation Rules</h3>
 * <ul>
 *   <li>Must not be null</li>
 *   <li>Must not be blank</li>
 *   <li>Must follow the allowed identity format</li>
 * </ul>
 *
 * <h3>Persistence Notes</h3>
 * <p>
 * Stored as a single embedded column.
 * This object is safe to use inside:
 * </p>
 * <ul>
 *   <li>{@link Embedded}</li>
 *   <li>Composite business references</li>
 *   <li>Audit metadata</li>
 * </ul>
 *
 * @see Actor
 * @see ActorIdentity
 * @see ActorType
 */
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActorCode {

    private static final Pattern FORMAT = Pattern.compile("^[A-Z0-9_\\-]{3,100}$");

    @Column(name = "actor_code", nullable = false, updatable = false)
    private String value;

    private ActorCode(String value) {
        validate(value);
        this.value = normalize(value);
    }

    /**
     * Factory method for creating a validated {@link ActorCode}.
     *
     * @param value {@link String} raw actor code
     * @return code {@link ActorCode} validated actor code
     */
    public static ActorCode of(String value) {
        return new ActorCode(value);
    }

    /**
     * Returns whether this actor code matches another actor code.
     *
     * @param other {@link ActorCode} other actor code
     * @return true if equal
     */
    public boolean sameAs(ActorCode other) {
        if (other == null) {
            return false;
        }

        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }

    private void validate(String value) {

        if (value == null || value.isBlank()) {
            throw IdentityException.invalidIdentity()
                    .withDetail("reason", "Actor code must not be null or blank");
        }

        String normalized = normalize(value);

        if (!FORMAT.matcher(normalized).matches()) {
            throw IdentityException.invalidIdentity()
                    .withDetail("reason", "Invalid actor code format")
                    .withDetail("actorCode", value);
        }
    }

    private String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }
}