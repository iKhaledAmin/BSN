package com.khaled_amin.book_social_network.identity.core.resolver;

import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.core.registry.ActorSourceResolverRegistry;


/**
 * Strategy interface responsible for transforming a domain-level {@link ActorSource}
 * into a business-level {@link Actor}.
 *
 * <p>
 * This interface enables domain entities to be interpreted as actors within the system,
 * forming a bridge between the domain model and the business abstraction model.
 * </p>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Convert {@link ActorSource} implementations into {@link Actor}</li>
 *   <li>Enable domain entities (e.g., Account, Client) to participate in identity workflows</li>
 *   <li>Support consistent identity resolution across application boundaries</li>
 * </ul>
 *
 * <h3>Resolution Mechanism</h3>
 * <p>
 * Implementations are registered in {@link ActorSourceResolverRegistry} and
 * selected based on {@link ActorType}.
 * </p>
 *
 * <ul>
 *   <li>{@link #getType()} defines which actor type this resolver supports</li>
 *   <li>The registry uses {@link ActorSource#getActorIdentity()} to determine routing</li>
 * </ul>
 *
 * <h3>Implementation Guidelines</h3>
 * <ul>
 *   <li>Each {@link ActorType} must have exactly one resolver</li>
 *   <li>Implementations must explicitly verify the source type</li>
 *   <li>Must not return {@code null}</li>
 *   <li>Should fail fast on mismatched source types</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Throw {@link TechnicalException} when the source type does not match</li>
 *   <li>This indicates a developer or configuration error, not a user error</li>
 *   <li>Must not attempt implicit casting or silent fallback</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * public class AccountActorSourceResolver implements ActorSourceResolver {
 *
 *     @Override
 *     public ActorType getActorType() {
 *         return ActorType.ACCOUNT;
 *     }
 *
 *     @Override
 *     public Actor resolve(ActorSource source) {
 *         if (!(source instanceof Account account)) {
 *             throw ActorResolutionException.sourceTypeMismatch(
 *                 Account.class,
 *                 source.getClass()
 *             );
 *         }
 *
 *         return new AccountActor(account.getId(), account.getRoleNames());
 *     }
 * }
 * }</pre>
 *
 * <h3>Architectural Notes</h3>
 * <ul>
 *   <li>This interface belongs to the core identity layer</li>
 *   <li>Implementations live in domain-specific modules (e.g., account, client)</li>
 *   <li>Works in conjunction with {@link ActorSource} contract</li>
 * </ul>
 *
 * <h3>⚠️ SECURITY Consideration</h3>
 * <p>
 * Since {@link ActorSource} is a sensitive contract, resolvers must ensure:
 * </p>
 * <ul>
 *   <li>The source is trusted and validated</li>
 *   <li>The identity mapping is correct and consistent</li>
 *   <li>No unintended entities are elevated to {@link Actor}</li>
 * </ul>
 *
 * @see Actor
 * @see ActorSource
 * @see ActorType
 * @see ActorSourceResolverRegistry
 * @see TechnicalException
 */

public interface ActorSourceResolver {

    /**
     * Returns the {@link ActorType} supported by this resolver.
     *
     * @return non-null actor type
     */
    ActorType getType();

    /**
     * Resolves the given {@link ActorSource} into an {@link Actor}.
     *
     * @param source {@link ActorSource} domain object implementing
     * @return non-null {@link Actor}
     * @throws TechnicalException if the source type does not match
     */
    Actor resolve(ActorSource source);
}