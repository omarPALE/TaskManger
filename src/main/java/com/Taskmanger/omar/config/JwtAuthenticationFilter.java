package com.Taskmanger.omar.config;

import com.Taskmanger.omar.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String requestPath = request.getServletPath();
        System.out.println("Processing request for path: " + requestPath);

        // Bypass authentication for Swagger or public endpoints
        if (requestPath.startsWith("/swagger-ui") || requestPath.startsWith("/v3/api-docs")) {
            System.out.println("Skipping authentication for: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found. Proceeding without authentication.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractEmail(jwt);
            System.out.println("Extracted email from JWT: " + userEmail);

            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Current authentication in SecurityContext: " + currentAuth);

            if (userEmail != null && currentAuth == null) {
                System.out.println("Loading user details for email: " + userEmail);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                System.out.println("User details loaded successfully for email: " + userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("JWT token is valid. Setting authentication.");

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set for user: " + userEmail);
                } else {
                    System.out.println("Invalid or expired JWT token.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error during JWT processing: " + e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
