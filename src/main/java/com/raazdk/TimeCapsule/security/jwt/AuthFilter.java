package com.raazdk.TimeCapsule.security.jwt;

import com.raazdk.TimeCapsule.security.service.TuserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    JwtUtils utils;

    @Autowired
    TuserService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extract JWT from the Authorization header
            String jwt = utils.extractJwt(request);

            // Validate the token and set the authentication if valid
            if (jwt != null && utils.validateJwt(jwt)) {
                String username = utils.getUsernameFromToken(jwt);

                // Load UserDetails from the UserDetailsService
                UserDetails details = userDetailsService.loadUserByUsername(username);

                // Create Authentication token with authorities
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (Exception e) {
            logger.error("An error occurred during JWT filtering: {}", e.getMessage());
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
