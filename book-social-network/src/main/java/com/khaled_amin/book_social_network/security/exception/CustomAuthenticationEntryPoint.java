package com.khaled_amin.book_social_network.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.core.api.ErrorResponse;
import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex
    ) throws IOException {

        SecurityException securityException = extractSecurityException(ex);

        SecurityError error = securityException.getError();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(securityException.getMessage())
                .details(Map.of())
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

    private SecurityException extractSecurityException(AuthenticationException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof SecurityException securityException) {
            return securityException;
        }

        return com.khaled_amin.book_social_network.security.exception
                .AuthenticationException
                .authenticationFailed();
    }

    private String buildAuthenticateHeader(SecurityError error) {

        String code = error.getCode();

        return switch (code) {

            case "SECURITY_TOKEN_EXPIRED" ->
                    "Bearer error=\"invalid_token\", error_description=\"The token expired\"";

            case "SECURITY_TOKEN_MISSING" ->
                    "Bearer error=\"invalid_token\", error_description=\"Token missing\"";

            case "SECURITY_TOKEN_INVALID",
                 "SECURITY_TOKEN_MALFORMED",
                 "SECURITY_TOKEN_SIGNATURE_INVALID" ->
                    "Bearer error=\"invalid_token\"";

            default ->
                    "Bearer";
        };
    }
}