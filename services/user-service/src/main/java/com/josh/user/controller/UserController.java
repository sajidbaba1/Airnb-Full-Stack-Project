package com.josh.user.controller;

import com.josh.user.dto.LoginRequestDTO;
import com.josh.user.dto.LoginResponseDTO;
import com.josh.user.dto.UserRequestDTO;
import com.josh.user.dto.UserResponseDTO;
import com.josh.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint to register a new passenger, seller, or admin user.
     * @Valid triggers automatic validation on the request body using JSR-380 annotations.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO response = userService.registerUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint to sign in and generate an access token.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Protected Profile Endpoint.
     * Returns profile details of the currently authenticated session.
     * 
     * LEARNING POINT:
     * We don't accept user email as a parameter. Passing user email as parameter is a security risk 
     * because any authenticated user could query other user profiles!
     * 
     * Instead, we fetch the caller identity from Spring Security Context:
     * SecurityContextHolder.getContext().getAuthentication().getPrincipal().
     * This principal was verified and populated earlier in the JwtAuthenticationFilter.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile() {
        // Retrieve the authenticated identity (email) from the security context
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        UserResponseDTO response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
}
