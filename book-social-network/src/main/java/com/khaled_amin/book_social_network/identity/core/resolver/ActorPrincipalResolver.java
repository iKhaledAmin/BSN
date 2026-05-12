package com.khaled_amin.book_social_network.identity.core.resolver;

import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

/**
 * Strategy interface responsible for transforming a security-level {@link AuthenticatedPrincipal}
 * into a business-level {@link Actor}.
 *
 * <p>
 * This interface is part of the identity resolution pipeline and serves as a bridge
 * between the security layer (e.g., authentication mechanisms) and the business abstraction model.
 * </p>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Convert framework-specific principals {@link AuthenticatedPrincipal} into domain-neutral {@link Actor}</li>
 *   <li>Decouple security infrastructure from business logic</li>
 *   <li>Enable support for multiple actor types (Account, Client, etc.)</li>
 * </ul>
 *
 * <h3>Resolution Mechanism</h3>
 * <p>
 * Implementations are registered in {@link ActorPrincipalResolverRegistry} and
 * selected based on {@link ActorType}.
 * </p>
 *
 * <ul>
 *   <li>{@link #getType()} defines which actor type this resolver supports</li>
 *   <li>The registry uses {@link AuthenticatedPrincipal#getActorType()} to determine routing</li>
 * </ul>
 *
 * <h3>Implementation Guidelines</h3>
 * <ul>
 *   <li>Each {@link ActorType} must have exactly one resolver</li>
 *   <li>Implementations must explicitly validate the principal type</li>
 *   <li>Must not return {@code null}</li>
 *   <li>Should fail fast on mismatched principal types</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Throw {@link ActorResolutionException} when the principal type does not match</li>
 *   <li>This indicates a developer or configuration error, not a user error</li>
 *   <li>Exceptions should not be translated to business-level exceptions</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * @Component
 * public class AccountActorPrincipalResolver implements ActorPrincipalResolver {
 *
 *     @Override
 *     public ActorType getActorType() {
 *         return ActorType.ACCOUNT;
 *     }
 *
 *     @Override
 *     public Actor resolve(AuthenticatedPrincipal principal) {
 *         if (!(principal instanceof AccountPrincipal account)) {
 *             throw ActorResolutionException.principalTypeMismatch(
 *                 AccountPrincipal.class,
 *                 principal.getClass()
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
 *   <li>Implementations live in feature modules (e.g., account, client)</li>
 *   <li>Prevents leakage of security framework details into domain/application layers</li>
 * </ul>
 *
 * @see Actor
 * @see AuthenticatedPrincipal
 * @see ActorType
 * @see ActorPrincipalResolverRegistry
 * @see ActorResolutionException
 */
public interface ActorPrincipalResolver {

    /**
     * Returns the {@link ActorType} supported by this resolver.
     *
     * @return non-null actor type
     */
    ActorType getType();

    /**
     * Resolves the given {@link AuthenticatedPrincipal} into an {@link Actor}.
     *
     * @param principal {@link AuthenticatedPrincipal} from the security layer
     * @return non-null {@link Actor}
     * @throws ActorResolutionException if the principal type does not match
     */
    Actor resolve(AuthenticatedPrincipal principal);
}