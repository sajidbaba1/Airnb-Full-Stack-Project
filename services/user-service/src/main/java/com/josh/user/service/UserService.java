package com.josh.user.service;

import com.josh.user.dto.LoginRequestDTO;
import com.josh.user.dto.LoginResponseDTO;
import com.josh.user.dto.UserRequestDTO;
import com.josh.user.dto.UserResponseDTO;

public interface UserService {
    /**
     * Registers a new user. Handles duplicate email validation and password hashing.
     */
    UserResponseDTO registerUser(UserRequestDTO dto);

    /**
     * Authenticates a user by validating credentials and returns a JWT response payload.
     */
    LoginResponseDTO login(LoginRequestDTO dto);

    /**
     * Retrieves the profile detail of a specific user.
     */
    UserResponseDTO getUserByEmail(String email);
}
