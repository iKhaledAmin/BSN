package com.khaled_amin.book_social_network.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.core.api.ErrorResponse;
import com.khaled_amin.book_social_network.core.logging.audit.SecurityEventLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private final SecurityEventLogger securityEventLogger;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex
    ) throws IOException {


        AuthorizationError error = AuthorizationError.ACCESS_DENIED;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(ex.getMessage())
                .details(Map.of())
                .path(request.getRequestURI())
                .build();

        response.setStatus(error.getStatus().value());
        response.setContentType("application/json");

        objectMapper.writeValue(
                response.getOutputStream(),
                ApiResponseFactory.error(errorResponse)
        );
    }
}