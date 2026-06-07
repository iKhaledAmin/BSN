package com.khaled_amin.book_social_network.core.logging.core;

import com.khaled_amin.book_social_network.core.logging.audit.RequestEventLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RequestCorrelationFilter extends OncePerRequestFilter {

    private final RequestIdGenerator requestIdGenerator;
    private final RequestEventLogger requestEventLogger;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String requestId = requestIdGenerator.generate();

        long startTime = System.currentTimeMillis();

        MDC.put(LoggingConstants.REQUEST_ID, requestId);

        try {

            requestEventLogger.logStart(
                    request.getMethod(),
                    request.getRequestURI()
            );

            filterChain.doFilter(request, response);

        }
        finally {

            long duration = System.currentTimeMillis() - startTime;

            requestEventLogger.logComplete(
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration
            );

            MDC.clear();
        }
    }
}