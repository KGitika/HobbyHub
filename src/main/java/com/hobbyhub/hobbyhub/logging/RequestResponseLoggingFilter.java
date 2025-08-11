package com.hobbyhub.hobbyhub.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Simple filter that logs every request and response. Logs request method/URI
 * and the corresponding response status. Any exception thrown during
 * processing is logged with full stack trace to aid debugging.
 */
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        logger.info("Incoming request: {} {}", method, uri);
        try {
            filterChain.doFilter(request, wrappedResponse);
        } catch (Exception ex) {
            logger.error("Error processing request: {} {}", method, uri, ex);
            throw ex;
        }
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
        logger.info("Response for {} {} -> {}", request.getMethod(), request.getRequestURI(), responseBody);

        wrappedResponse.copyBodyToResponse();
    }
}

