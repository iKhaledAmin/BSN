package com.khaled_amin.book_social_network.security.jwt.claims;

import com.khaled_amin.book_social_network.security.jwt.JwtService;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import io.jsonwebtoken.JwtBuilder;

/**
 * Strategy contract responsible for contributing actor-specific JWT claims
 * during token generation.
 *
 * <p>
 * This abstraction allows each authenticated principal type
 * (e.g. AccountPrincipal, ClientPrincipal)
 * to define its own JWT structure independently without coupling
 * {@link JwtService}
 * to actor-specific authorization logic.
 * </p>
 *
 * <p>
 * Typical use cases:
 * </p>
 * <ul>
 *     <li>Adding roles and permissions for human accounts</li>
 *     <li>Adding OAuth2 scopes for machine clients</li>
 *     <li>Adding custom claims for future actor types</li>
 * </ul>
 *
 * <p>
 * This follows the Strategy Pattern and supports Open/Closed Principle:
 * new actor types can introduce new JWT claim structures
 * without modifying the core JWT service.
 * </p>
 *
 * @param <T> supported authenticated principal type
 */
public interface JwtClaimsContributor<T extends AuthenticatedPrincipal> {

    /**
     * Returns the supported principal type handled by this contributor.
     *
     * @return supported principal class
     */
    Class<T> getSupportedPrincipal();

    /**
     * Contributes actor-specific claims into the JWT builder.
     *
     * @param builder JWT builder being populated
     * @param principal authenticated principal source
     */
    void contribute(JwtBuilder builder, T principal);
}