package com.josh.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginResponseDTO is returned upon successful authentication.
 * 
 * Includes the JWT authorization bearer token along with profile metadata 
 * so the frontend client (e.g. React) can customize UI states immediately 
 * (like showing admin buttons based on role) without extra profile queries.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
    private UserResponseDTO user;
}
