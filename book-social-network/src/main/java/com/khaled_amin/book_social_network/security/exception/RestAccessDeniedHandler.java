package com.khaled_amin.book_social_network.security.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.core.api.ErrorResponse;
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
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex
    ) throws IOException {

        SecurityError error = SecurityError.ACCESS_DENIED;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus().value())
                .code(error.getCode())
                .message(error.getMessage())
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