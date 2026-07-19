package com.josh.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * JwtAuthenticationFilter intercepts every incoming HTTP request.
 * 
 * Extends OncePerRequestFilter to guarantee execution exactly once per request dispatch.
 * 
 * --- FILTER FLOW: ---
 * 1. Checks if request contains "Authorization: Bearer <JWT>" header.
 * 2. Parses and validates the token with JwtUtil.
 * 3. Extracts user identity (email) and permissions (role).
 * 4. Populates Spring Security context, marking the request as authenticated.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. Extract Authorization header
            String authHeader = request.getHeader("Authorization");
            
            // 2. Validate header format
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Remove "Bearer " prefix
                
                // 3. cryptographically validate token
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getEmailFromToken(token);
                    String role = jwtUtil.getRoleFromToken(token);
                    
                    // 4. Map the role string to Spring GrantedAuthority (e.g. ROLE_USER)
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                    
                    // 5. Create UsernamePasswordAuthenticationToken (representing authenticated state)
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            email, 
                            null, // Password is null since user is already authenticated via token
                            authorities
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // 6. Set Authentication in SecurityContextHolder (accessible anywhere in this request execution thread)
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Successfully authenticated user {} with role {}", email, role);
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Forward the request/response to the next filter in the security filter chain
        filterChain.doFilter(request, response);
    }
}
