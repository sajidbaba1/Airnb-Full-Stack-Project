package com.josh.user.dto;

import com.josh.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * UserResponseDTO represents the safe user profile details returned to client.
 * 
 * IMPORTANT LEARNING POINT:
 * Notice that this DTO does NOT contain a 'password' field. Returning a database 
 * entity directly can easily leak password hashes or internal database columns 
 * in the JSON response. Utilizing a specific response DTO guarantees data safety.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean verified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
