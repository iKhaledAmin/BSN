package com.khaled_amin.book_social_network.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.core.api.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {

        SecurityError error = resolveSecurityError(ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(error.getMessage())
                .path(request.getRequestURI())
                .build();

        response.setHeader(
                "WWW-Authenticate",
                buildAuthenticateHeader(error)
        );

        response.setStatus(error.getStatus().value());
        response.setContentType("application/json");

        objectMapper.writeValue(
                response.getOutputStream(),
                ApiResponseFactory.error(errorResponse)
        );

    }

    private SecurityError resolveSecurityError(AuthenticationException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof SecurityException securityException &&
                securityException.getError() instanceof SecurityError securityError) {
            return securityError;
        }

        return SecurityError.AUTHENTICATION_FAILED;
    }

    private String buildAuthenticateHeader(SecurityError error) {

        return switch (error) {

            case TOKEN_EXPIRED ->
                    "Bearer error=\"invalid_token\", error_description=\"The token expired\"";

            case TOKEN_MISSING ->
                    "Bearer error=\"invalid_token\", error_description=\"Token missing\"";

            case TOKEN_INVALID,
                 TOKEN_MALFORMED,
                 TOKEN_SIGNATURE_INVALID ->
                    "Bearer error=\"invalid_token\"";

            default ->
                    "Bearer";
        };
    }
}