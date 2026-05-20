package com.khaled_amin.book_social_network.security.jwt;


import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.security.jwt.claims.JwtClaimsContributor;
import com.khaled_amin.book_social_network.security.jwt.claims.JwtClaimsContributorRegistry;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.exception.InvalidTokenException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public  class JwtService {
    private final JwtProperties jwtProperties;
    private final JwtClaimsContributorRegistry claimsContributorRegistry;


    private static final String CLAIM_ACTOR_TYPE = "actor_type";
    private static final String CLAIM_ACTOR_CODE = "actor_code";

    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_PERMISSIONS = "permissions";
    private static final String CLAIM_SCOPE = "scope";


    /**
     * Generates a signed JWT token for the given authenticated principal.
     *
     * @param principal {@link AuthenticatedPrincipal} principal used as the token identity source
     * @return token {@link String} signed JWT token
     */
    public String generateToken(AuthenticatedPrincipal principal) {
        return generateToken(new HashMap<>(), principal);
    }



    /**
     * Generates a signed JWT token for the provided authenticated principal.
     *
     * <p>
     * The token generation process consists of:
     * </p>
     * <ol>
     *     <li>Applying standard security claims
     *     (subject, actor type, actor code, timestamps)</li>
     *
     *     <li>Resolving the appropriate
     *     {@link JwtClaimsContributor}
     *     for the authenticated principal</li>
     *
     *     <li>Adding actor-specific authorization claims
     *     such as roles, permissions, or scopes</li>
     *
     *     <li>Signing the final JWT using the configured signing key</li>
     * </ol>
     *
     * <p>
     * Actor-specific claims are delegated to specialized contributors
     * to keep this service independent of authorization model details.
     * </p>
     *
     * @param extraClaims additional custom claims to include in the token
     * @param principal authenticated principal used as token identity source
     * @return signed JWT token
     * @throws IllegalStateException if no claims contributor is registered
     * for the provided principal type
     */
    public String generateToken(Map<String, Object> extraClaims, AuthenticatedPrincipal principal) {

        Date expirationDate = resolveExpirationDate(principal);

        JwtBuilder builder = Jwts.builder()
                .claims(extraClaims)
                .subject(principal.getSubject())
                .claim(CLAIM_ACTOR_TYPE, principal.getActorType().name())
                .claim(CLAIM_ACTOR_CODE, principal.getActorCode().getValue())
                .issuedAt(new Date())
                .expiration(expirationDate);

        JwtClaimsContributor<AuthenticatedPrincipal> contributor = claimsContributorRegistry.get(principal);

        contributor.contribute(builder, principal);

        return builder
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Extracts and validates the JWT payload from the provided token.
     *
     * <p>
     * This method validates token structure, signature, and required claims
     * before mapping the token into a {@link JwtPayload}.
     * </p>
     *
     * @param token {@link String} raw JWT token
     * @return payload {@link JwtPayload} extracted JWT payload
     * @throws InvalidTokenException if the token is invalid, malformed,
     * expired, unsupported, or missing required claims
     */
    public JwtPayload extractPayload(String token) {

        Claims claims = extractAllClaims(token);

        // SUBJECT
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            throw InvalidTokenException.invalid()
                    .withDebug("reason", "Token subject is missing");
        }

        ActorType actorType = extractActorType(claims);

        ActorCode actorCode = extractActorCode(claims);

        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        if (expiration == null) {
            throw InvalidTokenException.invalid()
                    .withDebug("reason", "Token expiration claim is missing");
        }

        Set<String> roles = extractRoles(claims);
        Set<String> permissions = extractPermissions(claims);
        Set<String> scopes = extractScopes(claims);

        // JWT PAYLOAD
        return new JwtPayload(
                subject,
                actorType,
                actorCode,
                issuedAt,
                expiration,
                roles,
                permissions,
                scopes
        );
    }

    /**
     * Validates that the provided JWT payload matches the authenticated principal.
     *
     * <p>
     * Validation includes:
     * </p>
     * <ul>
     *     <li>subject consistency</li>
     *     <li>actor type consistency</li>
     *     <li>token expiration</li>
     *     <li>principal active state</li>
     *     <li>principal locked state</li>
     * </ul>
     *
     * @param payload {@link JwtPayload} extracted JWT payload
     * @param principal {@link AuthenticatedPrincipal}resolved authenticated principal
     * @throws InvalidTokenException if validation fails
     */
    public void validateToken(JwtPayload payload, AuthenticatedPrincipal principal) {

        if (!principal.supportsToken(payload.getSubject())) {
            throw InvalidTokenException.invalid().withDebug("reason", "Token subject mismatch");
        }

        if (payload.getActorType() != principal.getActorType()) {
            throw InvalidTokenException.invalid().withDebug("reason", "Actor type mismatch");
        }

        if (!payload.getActorCode().equals(principal.getActorCode())) {
            throw InvalidTokenException.invalid().withDebug("reason", "Actor code mismatch");
        }

        if (isTokenExpired(payload)) {
            throw InvalidTokenException.invalid().withDebug("reason", "Token expired");
        }
        if (principal.isLocked()) {
            throw InvalidTokenException.principalLocked("Account is locked")
                    .withDebug("reason", "Account is locked")
                    .withDebug("getActorType", principal.getActorType().name())
                    .withDebug("subject", principal.getSubject());
        }
        if (!principal.isActive()) {
            throw InvalidTokenException.principalNotActive("Account is disabled or suspended")
                    .withDebug("reason", "Account is disabled or suspended")
                    .withDebug("getActorType", principal.getActorType())
                    .withDebug("username", principal.getSubject());
        }

    }


    /**
     * Extracts a specific claim from the provided JWT token.
     *
     * @param token {@link String} raw JWT token
     * @param claimsResolver function used to extract the target claim
     * @param <T> extracted claim type
     * @return extracted claim value
     * @throws InvalidTokenException if the token is invalid or cannot be parsed
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }





    private Date resolveExpirationDate(AuthenticatedPrincipal principal) {
        long expirationMinutes = jwtProperties.getExpirationMinutes(principal.getActorType());
        long expirationMillis = expirationMinutes * 60 * 1000;
        return new Date(System.currentTimeMillis() + expirationMillis);
    }

    private ActorType extractActorType(Claims claims) {

        try {

            String actorTypeRaw = claims.get(CLAIM_ACTOR_TYPE, String.class);

            if (actorTypeRaw == null || actorTypeRaw.isBlank()) {
                throw InvalidTokenException.invalid()
                        .withDebug("reason", "Actor type claim is missing");
            }

            return ActorType.from(actorTypeRaw);

        } catch (ActorResolutionException | RequiredTypeException ex) {
            throw InvalidTokenException.invalid(ex).withDebug("reason", "Invalid actor type");
        }
    }

    private ActorCode extractActorCode(Claims claims) {

        try {

            String actorCodeRaw = claims.get(CLAIM_ACTOR_CODE, String.class);

            if (actorCodeRaw == null || actorCodeRaw.isBlank()) {
                throw InvalidTokenException.invalid()
                        .withDebug("reason", "Actor code claim is missing");
            }

            return ActorCode.of(actorCodeRaw);

        } catch (RequiredTypeException ex) {

            throw InvalidTokenException.invalid(ex)
                    .withDebug("reason", "Invalid actor code");
        }
    }

    private Set<String> extractRoles(Claims claims) {

        Object raw = claims.get(CLAIM_ROLES);

        if (raw instanceof Collection<?> list) {
            return list.stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());
        }

        return Set.of();
    }

    private Set<String> extractPermissions(Claims claims) {

        Object raw = claims.get(CLAIM_PERMISSIONS);

        if (raw instanceof Collection<?> list) {
            return list.stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());
        }

        return Set.of();
    }

    private Set<String> extractScopes(Claims claims) {

        String raw = claims.get(CLAIM_SCOPE, String.class);

        if (raw == null || raw.isBlank()) {
            return Set.of();
        }

        return Arrays.stream(raw.split(" "))
                .collect(Collectors.toSet());
    }

    private boolean isTokenExpired(JwtPayload payload) {
        return payload.getExpiration().before(new Date());
    }

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * @param token {@link String} raw JWT token
     * @return date {@link Date} token expiration date
     * @throws InvalidTokenException if the token is invalid or cannot be parsed
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {

        try {

            return Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException ex) {
            throw InvalidTokenException.expired(ex).withDebug("reason", "JWT token expired");
        } catch (MalformedJwtException ex) {
            throw InvalidTokenException.malformed(ex).withDebug("reason", "Malformed JWT token");
        } catch (SignatureException ex) {
            throw InvalidTokenException.signatureInvalid(ex).withDebug("reason", "Invalid JWT signature");
        } catch (UnsupportedJwtException ex) {
            throw InvalidTokenException.invalid(ex).withDebug("reason", "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw InvalidTokenException.invalid(ex).withDebug("reason", "JWT token is empty or invalid");
        }

    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
