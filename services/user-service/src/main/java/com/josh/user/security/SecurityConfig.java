package com.josh.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig defines the central security configuration rules.
 * 
 * 1. @Configuration: Tells Spring this is a configuration class defining Bean methods.
 * 2. @EnableWebSecurity: Integrates Spring Security with our application.
 * 3. @EnableMethodSecurity: Enables role-based annotation controls (e.g. @PreAuthorize("hasRole('ADMIN')")) on controllers.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures request filters, authorization scopes, and stateless session policies.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF (Cross-Site Request Forgery). Stateless JWT backends do not store 
            // session cookies, rendering standard cookie-based CSRF attacks irrelevant.
            .csrf(csrf -> csrf.disable())
            
            // 2. Set Session policy to STATELESS. Prevents Spring from storing session states in memory.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 3. Define authorize rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Open endpoints
                .anyRequest().authenticated() // Protect all other endpoints
            )
            
            // 4. Register our Custom JWT Filter before UsernamePasswordAuthenticationFilter in the execution chain.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Declares the BCrypt password encoder bean.
     * BCrypt is a secure, slow hashing algorithm that incorporates a random salt value on every execution,
     * protecting against dictionary and rainbow table database attacks.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes standard AuthenticationManager bean to handle user credential checking.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
