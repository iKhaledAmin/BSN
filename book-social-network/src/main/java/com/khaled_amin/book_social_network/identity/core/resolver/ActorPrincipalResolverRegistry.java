package com.khaled_amin.book_social_network.identity.core.resolver;

import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Central registry responsible for managing and delegating {@link ActorPrincipalResolver}
 * implementations based on {@link ActorType}.
 *
 * <p>
 * This component is part of the identity resolution pipeline and acts as a dispatcher
 * that selects the appropriate resolver to transform a security-level
 * {@link AuthenticatedPrincipal} into a business-level {@link Actor}.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Maintain a one-to-one mapping between {@link ActorType} and {@link ActorPrincipalResolver}</li>
 *   <li>Route incoming principals to the correct resolver</li>
 *   <li>Enforce uniqueness of resolver registration per actor type</li>
 * </ul>
 *
 * <h3>Initialization Contract</h3>
 * <ul>
 *   <li>All resolvers are injected via Spring and registered at startup</li>
 *   <li>Each {@link ActorType} must have exactly one resolver</li>
 *   <li>Duplicate resolvers for the same type will cause application startup failure</li>
 * </ul>
 *
 * <h3>Thread Safety</h3>
 * <p>
 * The registry is immutable after initialization and safe for concurrent use.
 * </p>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Throws {@link ActorResolutionException} if:
 *       <ul>
 *           <li>No resolver is registered for a given {@link ActorType}</li>
 *           <li>Duplicate resolvers are detected during initialization</li>
 *       </ul>
 *   </li>
 *   <li>All failures indicate configuration or developer errors, not user errors</li>
 * </ul>
 *
 * @see ActorPrincipalResolver
 * @see ActorType
 * @see ActorResolutionException
 */
@Component
public class ActorPrincipalResolverRegistry {

    private final Map<ActorType, ActorPrincipalResolver> resolverMap;

    public ActorPrincipalResolverRegistry(List<ActorPrincipalResolver> resolvers) {

        Map<ActorType, ActorPrincipalResolver> map = new EnumMap<>(ActorType.class);

        for (ActorPrincipalResolver resolver : resolvers) {

            ActorType type = resolver.getType();

            if (map.containsKey(type)) {
                throw ActorResolutionException.registryConflict(type);
            }

            map.put(type, resolver);
        }

        this.resolverMap = Map.copyOf(map);
    }




/**
 * Resolves the given {@link AuthenticatedPrincipal} into a corresponding {@link Actor}.
 *
 * <p>
 * This method determines the appropriate {@link ActorPrincipalResolver} based on
 * {@link AuthenticatedPrincipal#getActorType()} and delegates the resolution process.
 * </p>
 *
 * <h3>Resolution Flow</h3>
 * <ol>
 *   <li>Extract {@link ActorType} from the principal</li>
 *   <li>Locate the matching resolver in the registry</li>
 *   <li>Delegate transformation to the resolver</li>
 * </ol>
 *
 * <h3>Preconditions</h3>
 * <ul>
 *   <li>{@code principal} must not be {@code null}</li>
 *   <li>{@link AuthenticatedPrincipal#getActorType()} must not be {@code null}</li>
 *   <li>A resolver must be registered for the given {@link ActorType}</li>
 * </ul>
 *
 * <h3>Postconditions</h3>
 * <ul>
 *   <li>Returns a non-null {@link Actor}</li>
 * </ul>
 *
 * <h3>Failure Scenarios</h3>
 * <ul>
 *   <li>If no resolver is registered for the actor type:
 *       <ul>
 *           <li>{@link ActorResolutionException} is thrown</li>
 *       </ul>
 *   </li>
 *   <li>If the resolver detects a type mismatch internally:
 *       <ul>
 *           <li>{@link ActorResolutionException} is thrown</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <h3>Important Notes</h3>
 * <ul>
 *   <li>This method assumes the principal has already been authenticated</li>
 *   <li>No fallback behavior is applied at this level</li>
 *   <li>This method must not be used with arbitrary or untrusted principal implementations</li>
 * </ul>
 *
 * @param principal {@link AuthenticatedPrincipal} principal from the security layer
 * @return resolved {@link Actor}
 * @throws ActorResolutionException if resolution fails due to configuration or type mismatch
 */
    public Actor resolve(AuthenticatedPrincipal principal) {

        ActorType type = principal.getActorType();
        ActorPrincipalResolver resolver = resolverMap.get(type);

        if (resolver == null) {
            throw ActorResolutionException.resolverNotFound(type);
        }

        return resolver.resolve(principal);
    }
}