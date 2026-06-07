package com.khaled_amin.book_social_network.security.Spring_integration;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.core.logging.audit.SecurityEventLogger;
import com.khaled_amin.book_social_network.core.logging.core.ActorLoggingContext;
import com.khaled_amin.book_social_network.security.exception.JwtAuthenticationException;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import com.khaled_amin.book_social_network.security.principal.core.PrincipalResolverRegistry;
import com.khaled_amin.book_social_network.security.jwt.JwtPayload;
import com.khaled_amin.book_social_network.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final PrincipalResolverRegistry resolverRegistry;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final SecurityEventLogger securityEventLogger;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Skip if already authenticated
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            // Parse token (snapshot of the token)
            JwtPayload payload = jwtService.extractPayload(token);

            // Resolve correct principal (ACCOUNT / CLIENT)
            AuthenticatedPrincipal principal = resolverRegistry.resolve(payload);

            // Validate using PAYLOAD (NOT TOKEN)
            jwtService.validateToken(payload, principal);

            // Set actor context
            ActorLoggingContext.put(principal); // here we put the principal in the MDC (any date we needed along the request)

            // Build Authentication
            UsernamePasswordAuthenticationToken auth = buildAuthentication(principal);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Log authentication success
            securityEventLogger.authenticationSucceeded(principal);

        } catch (SecurityException | TechnicalException ex)  {

            // Log authentication failures
            if (ex instanceof SecurityException securityException){
                securityEventLogger.authenticationFailed(securityException);
            }

            SecurityContextHolder.clearContext();

            authenticationEntryPoint.commence(request, response, new JwtAuthenticationException(ex));

            return;
        }

        filterChain.doFilter(request, response);
    }



    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        return header.substring(7);
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(AuthenticatedPrincipal principal) {

        return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
    }
}