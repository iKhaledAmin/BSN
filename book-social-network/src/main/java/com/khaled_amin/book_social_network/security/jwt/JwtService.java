package com.khaled_amin.book_social_network.security.jwt;


import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.security.exception.AuthenticationException;
import com.khaled_amin.book_social_network.security.jwt.claims.JwtClaimsContributor;
import com.khaled_amin.book_social_network.security.jwt.claims.JwtClaimsContributorRegistry;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
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
     * to keep this service independent of authorization model clientDetails.
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
     * @throws SecurityException if the token is invalid, malformed,
     * expired, unsupported, or missing required claims
     */
    public JwtPayload extractPayload(String token) {

        Claims claims = extractAllClaims(token);

        // ================= ACTOR =================

        ActorType actorType = extractActorType(claims);

        ActorCode actorCode = extractActorCode(claims);

        // ================= SUBJECT =================

        String subject = claims.getSubject();

        if (subject == null || subject.isBlank()) {

            throw AuthenticationException.invalidToken()
                    .withDebugDetails("reason", "Token subject is missing");
        }



        // ================= TIME CLAIMS =================

        Date issuedAt = claims.getIssuedAt();

        Date expiration = claims.getExpiration();

        if (expiration == null) {

            throw AuthenticationException.invalidToken()
                    .withDebugDetails("reason", "Token expiration claim is missing");
        }

        // ================= AUTHORIZATION CLAIMS =================

        Set<String> roles = extractRoles(claims);

        Set<String> permissions = extractPermissions(claims);

        Set<String> scopes = extractScopes(claims);

        // ================= PAYLOAD =================

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
     * @throws SecurityException if validation fails
     */
    public void validateToken(JwtPayload payload, AuthenticatedPrincipal principal) {

        // ================= TOKEN CONSISTENCY =================

        if (!principal.supportsToken(payload.getSubject())) {

            throw AuthenticationException.invalidToken()
                    .withDebugDetails("reason", "Token subject mismatch")
                    .withDebugDetails("tokenSubject", payload.getSubject())
                    .withDebugDetails("principalSubject", principal.getSubject());
        }

        if (payload.getActorType() != principal.getActorType()) {

            throw AuthenticationException.invalidToken()
                    .withDebugDetails("reason", "Actor type mismatch")
                    .withDebugDetails("tokenActorType", payload.getActorType())
                    .withDebugDetails("principalActorType", principal.getActorType());
        }

        if (!payload.getActorCode().equals(principal.getActorCode())) {

            throw AuthenticationException.invalidToken()
                    .withDebugDetails("reason", "Actor code mismatch")
                    .withDebugDetails("tokenActorCode", payload.getActorCode())
                    .withDebugDetails("principalActorCode", principal.getActorCode());
        }

        // ================= TOKEN STATE =================

        if (isTokenExpired(payload)) {

            throw AuthenticationException.expiredToken()
                    .withDebugDetails("reason", "Token expired")
                    .withDebugDetails("expiration", payload.getExpiration());
        }

        // ================= PRINCIPAL STATE =================

        if (principal.isLocked()) {
            String principalType = principal.getActorType().name();
            throw AuthenticationException.principalLocked(principalType.toLowerCase(Locale.ROOT))
                    .withDebugDetails("reason", "Principal is locked")
                    .withDebugDetails("actorType",principalType)
                    .withDebugDetails("subject", principal.getSubject());
        }

        if (!principal.isActive()) {
            String principalType = principal.getActorType().name();
            throw AuthenticationException.principalInactive(principalType.toLowerCase(Locale.ROOT))
                    .withDebugDetails("reason", "Principal is inactive")
                    .withDebugDetails("actorType",principalType)
                    .withDebugDetails("subject", principal.getSubject());
        }
    }


    /**
     * Extracts a specific claim from the provided JWT token.
     *
     * @param token {@link String} raw JWT token
     * @param claimsResolver function used to extract the target claim
     * @param <T> extracted claim type
     * @return extracted claim value
     * @throws SecurityException if the token is invalid or cannot be parsed
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
                throw AuthenticationException.invalidToken()
                        .withDebugDetails("reason", "Actor type claim is missing");
            }

            return ActorType.from(actorTypeRaw);

        } catch (RequiredTypeException | ValidationException ex) {
            throw AuthenticationException.invalidToken(ex)
                    .withDebugDetails("reason", "Invalid actor type");
        }
    }

    private ActorCode extractActorCode(Claims claims) {

        try {

            String actorCodeRaw = claims.get(CLAIM_ACTOR_CODE, String.class);

            if (actorCodeRaw == null || actorCodeRaw.isBlank()) {

                throw AuthenticationException.invalidToken()
                        .withDebugDetails("reason", "Actor code claim is missing");
            }

            return ActorCode.of(actorCodeRaw);

        } catch (RequiredTypeException | ValidationException ex) {

            throw AuthenticationException.invalidToken(ex)
                    .withDebugDetails("reason", "Invalid actor code");
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
     * @throws SecurityException if the token is invalid or cannot be parsed
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

            throw AuthenticationException.expiredToken(ex)
                    .withDebugDetails("reason", "Token expired")
                    .withDebugDetails("expiration", ex.getClaims().getExpiration());

        } catch (MalformedJwtException ex) {

            throw AuthenticationException.malformedToken(ex)
                    .withDebugDetails("reason", "Malformed JWT token");

        } catch (SignatureException ex) {

            throw AuthenticationException.invalidTokenSignature(ex)
                    .withDebugDetails("reason", "Invalid JWT signature");

        } catch (UnsupportedJwtException ex) {

            throw AuthenticationException.invalidToken(ex)
                    .withDebugDetails("reason", "Unsupported JWT token");

        } catch (IllegalArgumentException ex) {

            throw AuthenticationException.invalidToken(ex)
                    .withDebugDetails("reason", "JWT token is missing or empty");
        }
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
