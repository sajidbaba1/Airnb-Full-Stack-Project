package com.josh.user.mapper;

import com.josh.user.dto.UserRequestDTO;
import com.josh.user.dto.UserResponseDTO;
import com.josh.user.entity.User;

/**
 * UserMapper handles conversions between User entity and DTO classes.
 * 
 * Manually written mapping routines provide high performance, zero runtime reflection 
 * overhead, and are extremely easy to debug compared to reflection-heavy mapping 
 * libraries like ModelMapper or MapStruct.
 */
public class UserMapper {

    /**
     * Map UserRequestDTO into User Entity.
     * Note: We map the plain text password here, but it MUST be hashed in the service layer 
     * using BCrypt before persisting to the database.
     */
    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail().toLowerCase().trim())
                .password(dto.getPassword())
                .role(dto.getRole())
                .verified(false) // Initial registration is unverified
                .build();
    }

    /**
     * Map User Entity into UserResponseDTO for client responses.
     * Note: Excludes security-sensitive fields like password hash.
     */
    public static UserResponseDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        return UserResponseDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .verified(entity.isVerified())
                .lastLogin(entity.getLastLogin())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
