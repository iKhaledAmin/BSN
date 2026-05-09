package com.khaled_amin.book_social_network.identity.core.resolver;

import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;


/**
 * Central registry responsible for managing and delegating {@link ActorSourceResolver}
 * implementations based on {@link ActorType}.
 *
 * <p>
 * This component enables domain entities that implementing {@link ActorSource} to be
 * transformed into business-level {@link Actor} representations.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Maintain a mapping between {@link ActorType} and {@link ActorSourceResolver}</li>
 *   <li>Route domain objects to the appropriate resolver</li>
 *   <li>Enforce strict type-based resolution rules</li>
 * </ul>
 *
 * <h3>Initialization Contract</h3>
 * <ul>
 *   <li>All resolvers are registered at application startup</li>
 *   <li>Each {@link ActorType} must have exactly one resolver</li>
 *   <li>Duplicate resolvers result in startup failure</li>
 * </ul>
 *
 * <h3>Thread Safety</h3>
 * <p>
 * This registry is immutable and safe for concurrent access.
 * </p>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Throws {@link ActorResolutionException} if:
 *       <ul>
 *           <li>No resolver exists for a given actor type</li>
 *           <li>Duplicate resolvers are detected during initialization</li>
 *       </ul>
 *   </li>
 *   <li>Failures indicate system misconfiguration or developer mistake</li>
 * </ul>
 *
 * <h3>⚠️ Security Consideration</h3>
 * <ul>
 *   <li>{@link ActorSource} is a sensitive contract</li>
 *   <li>Only trusted domain entities should implement it</li>
 *   <li>Improper use may lead to incorrect identity elevation</li>
 * </ul>
 *
 * @see ActorSourceResolver
 * @see ActorSource
 * @see ActorResolutionException
 */

@Component
public class ActorSourceResolverRegistry {
    private final Map<ActorType, ActorSourceResolver> resolverMap;

    public ActorSourceResolverRegistry(List<ActorSourceResolver> resolvers) {

        Map<ActorType, ActorSourceResolver> map = new EnumMap<>(ActorType.class);

        for (ActorSourceResolver resolver : resolvers) {

            ActorType type = resolver.getType();

            if (map.containsKey(type)) {
                throw ActorResolutionException.registryConflict(type);
            }

            map.put(type, resolver);
        }

        this.resolverMap = Map.copyOf(map);
    }




    /**
     * Resolves a given {@link ActorSource} into a corresponding {@link Actor}.
     *
     * <p>
     * The resolution is performed by:
     * </p>
     * <ol>
     *   <li>Extracting the {@link ActorType} from {@link ActorSource#getActorIdentity()}</li>
     *   <li>Locating the appropriate {@link ActorSourceResolver}</li>
     *   <li>Delegating the transformation</li>
     * </ol>
     *
     * <h3>Preconditions</h3>
     * <ul>
     *   <li>{@code source} must not be {@code null}</li>
     *   <li>{@link ActorSource#getActorIdentity()} must return a valid identity</li>
     *   <li>A resolver must be registered for the extracted {@link ActorType}</li>
     * </ul>
     *
     * <h3>Postconditions</h3>
     * <ul>
     *   <li>Returns a non-null {@link Actor}</li>
     * </ul>
     *
     * <h3>Failure Scenarios</h3>
     * <ul>
     *   <li>If no resolver is found:
     *       <ul>
     *           <li>{@link ActorResolutionException} is thrown</li>
     *       </ul>
     *   </li>
     *   <li>If the resolver detects a type mismatch:
     *       <ul>
     *           <li>{@link ActorResolutionException} is thrown</li>
     *       </ul>
     *   </li>
     * </ul>
     *
     * <h3>Important Notes</h3>
     * <ul>
     *   <li>This method assumes the source is a trusted domain object</li>
     *   <li>No fallback behavior is applied</li>
     *   <li>Improper implementation of {@link ActorSource} may break resolution guarantees</li>
     * </ul>
     *
     * @param source domain object implementing {@link ActorSource}
     * @return resolved {@link Actor}
     * @throws ActorResolutionException if resolution fails due to configuration or type mismatch
     */
    public Actor resolve(ActorSource source) {

        ActorType type = source.getActorIdentity().type();
        ActorSourceResolver resolver = resolverMap.get(type);

        if (resolver == null) {
            throw ActorResolutionException.resolverNotFound(type);
        }

        return resolver.resolve(source);
    }

}
