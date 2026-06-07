package com.khaled_amin.book_social_network.security.principal.core;

import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.jwt.JwtPayload;

/**
 * Strategy contract responsible for transforming a validated authentication token payload
 * into a security-level {@link AuthenticatedPrincipal}.
 *
 * <p>
 * This interface is part of the authentication resolution pipeline and acts as the bridge
 * between token-based authentication data ({@link JwtPayload}) and the application's
 * security principal model ({@link AuthenticatedPrincipal}).
 * </p>
 *
 * <h3>Purpose</h3>
 * <ul>
 *   <li>Convert JWT authentication claims into a concrete security principal</li>
 *   <li>Support multiple authentication domains (e.g., ACCOUNT, CLIENT)</li>
 *   <li>Decouple authentication source resolution from Spring SECURITY internals</li>
 * </ul>
 *
 * <h3>Resolution Model</h3>
 * <p>
 * Implementations are selected by {@link PrincipalResolverRegistry} using {@link ActorType}
 * extracted from {@link JwtPayload}.
 * Each resolver is responsible for a single actor type.
 * </p>
 *
 * <h3>Contract Rules</h3>
 * <ul>
 *   <li>{@link #getType()} must uniquely identify the supported {@link ActorType}</li>
 *   <li>{@link #resolve(JwtPayload)} must return a fully initialized {@link AuthenticatedPrincipal}</li>
 *   <li>Implementations must not return {@code null}</li>
 *   <li>Resolution must be deterministic and stateless</li>
 * </ul>
 *
 * <h3>Input Constraints</h3>
 * <ul>
 *   <li>{@code payload} is assumed to be already validated (signature, expiration, etc.)</li>
 *   <li>{@link JwtPayload#getSubject()} must correspond to an existing identity in persistence</li>
 *   <li>{@link JwtPayload#getActorType()} must match the resolver type</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Identity lookup failures (e.g., user not found) must be handled by throwing
 *       a security-related runtime exception (e.g., {@link SecurityException})</li>
 *   <li>Type mismatches or unsupported payloads are considered configuration errors</li>
 *   <li>No fallback principal should be created on failure</li>
 * </ul>
 *
 * <h3>Design Notes</h3>
 * <ul>
 *   <li>This interface belongs to the security layer</li>
 *   <li>It is invoked during authentication processing only</li>
 *   <li>It must remain independent of business/domain models</li>
 * </ul>
 *
 * @see AuthenticatedPrincipal
 * @see JwtPayload
 * @see ActorType
 * @see PrincipalResolverRegistry
 */
public interface PrincipalResolver {

    /**
     * Returns the {@link ActorType} supported by this resolver.
     *
     * <p>
     * Used by {@link PrincipalResolverRegistry} to route resolution requests.
     * Each actor type must have exactly one resolver.
     * </p>
     *
     * @return non-null {@link ActorType} supported actor type
     */
    ActorType getType();

    /**
     * Resolves a validated JWT payload into a security principal.
     *
     * <p>
     * The implementation is responsible for:
     * </p>
     * <ul>
     *   <li>Loading the corresponding identity from persistence</li>
     *   <li>Mapping identity data into a concrete {@link AuthenticatedPrincipal}</li>
     *   <li>Ensuring consistency between payload and stored identity</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code payload} must not be {@code null}</li>
     *   <li>{@link JwtPayload#getSubject()} must reference a valid identity</li>
     *   <li>{@link JwtPayload#getActorType()} must match {@link #getType()}</li>
     * </ul>
     *
     * <h3>Failure Semantics</h3>
     * <ul>
     *   <li>If identity is not found → throw authentication-related exception</li>
     *   <li>If payload cannot be mapped → throw runtime security exception</li>
     *   <li>No silent fallback or anonymous principal creation is allowed</li>
     * </ul>
     *
     * @param payload {@link JwtPayload} validated JWT payload containing identity claims
     * @return fully constructed {@link AuthenticatedPrincipal}
     * @throws SecurityException if identity resolution fails or is inconsistent
     */
    AuthenticatedPrincipal resolve(JwtPayload payload);
}