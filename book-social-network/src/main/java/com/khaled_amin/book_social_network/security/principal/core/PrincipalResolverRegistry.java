package com.khaled_amin.book_social_network.security.principal.core;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.exception.SecurityTechnicalException;
import com.khaled_amin.book_social_network.security.jwt.JwtPayload;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Central registry responsible for resolving a {@link JwtPayload} into a concrete
 * {@link AuthenticatedPrincipal} based on the associated {@link ActorType}.
 *
 * <p>
 * This component is part of the authentication resolution pipeline and acts as a dispatcher
 * between token-level identity data and security principal construction.
 * </p>
 *
 * <h3>Role in the System</h3>
 * <ul>
 *   <li>Maps {@link ActorType} → {@link PrincipalResolver}</li>
 *   <li>Delegates principal creation to the appropriate resolver implementation</li>
 *   <li>Ensures strict one-to-one mapping between actor types and resolvers</li>
 * </ul>
 *
 * <h3>Resolution Flow</h3>
 * <ol>
 *   <li>Extract {@link ActorType} from {@link JwtPayload}</li>
 *   <li>Locate the corresponding {@link PrincipalResolver}</li>
 *   <li>Delegate resolution to the selected resolver</li>
 * </ol>
 *
 * <h3>Constraints</h3>
 * <ul>
 *   <li>Each {@link ActorType} must have exactly one registered resolver</li>
 *   <li>Resolvers must be deterministic and stateless</li>
 *   <li>Registry is immutable after initialization</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>If multiple resolvers are registered for the same {@link ActorType}:
 *       {@link TechnicalException} is thrown at startup</li>
 *   <li>If no resolver exists for a given {@link ActorType}:
 *       {@link {@link TechnicalException} is thrown at startup</li>#nullPrincipalResolver(ActorType)} is thrown at runtime</li>
 *   <li>All failures are considered system-level configuration errors, not user errors</li>
 * </ul>
 *
 * <h3>SECURITY Notes</h3>
 * <ul>
 *   <li>This component operates only on validated JWT payloads</li>
 *   <li>No authentication or validation is performed here</li>
 *   <li>It assumes payload integrity has already been verified by the security layer</li>
 * </ul>
 *
 * <h3>Architectural Position</h3>
 * <ul>
 *   <li>Belongs to the security infrastructure layer</li>
 *   <li>Used exclusively during authentication processing</li>
 *   <li>Acts as a bridge between JWT decoding and principal construction</li>
 * </ul>
 *
 * @see PrincipalResolver
 * @see AuthenticatedPrincipal
 * @see JwtPayload
 * @see ActorType
 * @see TechnicalException
 */
@Component
public class PrincipalResolverRegistry {

    private final Map<ActorType, PrincipalResolver> principalResolverMap;

    public PrincipalResolverRegistry(List<PrincipalResolver> resolvers) {

        Map<ActorType, PrincipalResolver> map = new EnumMap<>(ActorType.class);

        for (PrincipalResolver resolver : resolvers) {
            ActorType type = resolver.getType();

            if (map.containsKey(type)) {
                throw SecurityTechnicalException.duplicatePrincipalResolver(type);
            }

            map.put(type, resolver);
        }

        this.principalResolverMap = Map.copyOf(map);
    }



    /**
     * Resolves a validated {@link JwtPayload} into a concrete
     * {@link AuthenticatedPrincipal} using the registered
     * {@link PrincipalResolver} associated with the payload {@link ActorType}.
     *
     * <p>
     * This method acts as the central dispatch point of the principal
     * resolution pipeline.
     * </p>
     *
     * <h3>Resolution Process</h3>
     * <ol>
     *   <li>Extract the {@link ActorType} from the provided {@link JwtPayload}</li>
     *   <li>Locate the matching {@link PrincipalResolver}</li>
     *   <li>Delegate principal construction to the resolver</li>
     * </ol>
     *
     * <h3>Input Assumptions</h3>
     * <ul>
     *   <li>The JWT payload has already been cryptographically validated</li>
     *   <li>The payload subject and actor type are trusted</li>
     *   <li>The payload is not {@code null}</li>
     * </ul>
     *
     * <h3>Resolver Responsibilities</h3>
     * <p>
     * The selected {@link PrincipalResolver} is responsible for:
     * </p>
     * <ul>
     *   <li>Loading the corresponding identity from persistence</li>
     *   <li>Ensuring the identity exists and is resolvable</li>
     *   <li>Constructing the correct {@link AuthenticatedPrincipal}</li>
     * </ul>
     *
     * <h3>Failure Semantics</h3>
     * <ul>
     *   <li>
     *       If no resolver exists for the payload actor type:
     *       {@link TechnicalException}
     *       is thrown.
     *   </li>
     *   <li>
     *       If the resolver fails to resolve the principal:
     *       a security-related runtime exception is propagated.
     *   </li>
     *   <li>
     *       No fallback or anonymous principal is created on failure.
     *   </li>
     * </ul>
     *
     * <h3>SECURITY Notes</h3>
     * <ul>
     *   <li>This method performs no JWT validation</li>
     *   <li>This method performs no authorization checks</li>
     *   <li>Returned principals are considered trusted authentication identities</li>
     * </ul>
     *
     * @param payload {@link JwtPayload} validated JWT payload containing identity claims
     * @return resolved principal {@link AuthenticatedPrincipal} associated with the payload
     * @throws TechnicalException if principal resolution fails
     */
    public AuthenticatedPrincipal resolve(JwtPayload payload) {

        PrincipalResolver resolver = principalResolverMap.get(payload.getActorType());

        if (resolver == null) {
            throw SecurityTechnicalException.nullPrincipalResolver(payload.getActorType());
        }

        return resolver.resolve(payload);
    }
}