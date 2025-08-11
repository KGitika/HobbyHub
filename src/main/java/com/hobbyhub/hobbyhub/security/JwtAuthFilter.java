package com.hobbyhub.hobbyhub.security;

import com.hobbyhub.hobbyhub.service.JwtService;
import com.hobbyhub.hobbyhub.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Simple JWT authentication filter that extracts the user id from the token and
 * sets it in the Spring Security context.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No bearer token found for {} {}", request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String userId = jwtService.validate(token);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userService.getUserById(Long.parseLong(userId)).ifPresent(user -> {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, java.util.List.of());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Authenticated user {} for {} {}", userId, request.getMethod(), request.getRequestURI());
                });
            }
        } catch (Exception ex) {
            logger.error("Authentication error for token {}", token, ex);
        }

        filterChain.doFilter(request, response);
    }
}

