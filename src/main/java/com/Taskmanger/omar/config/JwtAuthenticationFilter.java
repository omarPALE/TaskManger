package com.Taskmanger.omar.config;


import com.Taskmanger.omar.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractEmail(jwt);
            System.out.println("Extracted username from JWT: " + userEmail);  // Debugging line

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Fucj you"+authentication);  // Debugging line

            if (userEmail != null && authentication == null) {
                System.out.println("Fucj me");  // Debugging line

                System.out.println("Loading user details for: " + userEmail);  // Before query
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("User details loaded for: " + userEmail);  // After query

                System.out.println("Fucj me2");  // Debugging line

                System.out.println("Fuck you from inside"+ userDetails.toString());  // Debugging line

                System.out.println("UserDetails loaded: " + userDetails.getUsername());  // Debugging line

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("Token is valid");  // Debugging line

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                else {
                    System.out.println("Token is invalid or expired");  // Debugging line
                }
            }
            System.out.println("Fucj me after if");  // Debugging line

            filterChain.doFilter(request, response);
        }  catch (UsernameNotFoundException e) {
            System.out.println("User not found: " + userDetailsService.toString());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

}