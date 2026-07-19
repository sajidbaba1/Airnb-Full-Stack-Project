package com.josh.user.service.impl;

import com.josh.common.exception.ResourceNotFoundException;
import com.josh.user.dto.LoginRequestDTO;
import com.josh.user.dto.LoginResponseDTO;
import com.josh.user.dto.UserRequestDTO;
import com.josh.user.dto.UserResponseDTO;
import com.josh.user.entity.User;
import com.josh.user.mapper.UserMapper;
import com.josh.user.repository.UserRepository;
import com.josh.user.security.JwtUtil;
import com.josh.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        log.info("Attempting to register user: {}", dto.getEmail());
        
        // 1. Validate if email is already taken.
        if (userRepository.findByEmail(dto.getEmail().toLowerCase().trim()).isPresent()) {
            log.error("Registration failed. Email already registered: {}", dto.getEmail());
            throw new IllegalArgumentException("Email already exists in the system.");
        }

        // 2. Map DTO input parameters into a fresh database User Entity.
        User user = UserMapper.toEntity(dto);

        // 3. Cryptographically hash the plain-text password using BCrypt.
        // Plain text: "123456" -> Hashed: "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.heWG/igi"
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 4. Persist to DB.
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        return UserMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("Processing login request for user: {}", dto.getEmail());

        // 1. Fetch user by email.
        User user = userRepository.findByEmail(dto.getEmail().toLowerCase().trim())
                .orElseThrow(() -> {
                    log.error("Login failed. User not found: {}", dto.getEmail());
                    return new ResourceNotFoundException("Invalid email or password.");
                });

        // 2. Check password matches the database hash.
        // IMPORTANT SECURITY LEARNING:
        // BCrypt hashes cannot be decrypted. Instead, passwordEncoder.matches() pulls the salt from 
        // the saved hash, applies it to the raw input password, hashes it, and checks if the hashes match.
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.error("Login failed. Password mismatch for user: {}", dto.getEmail());
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // 3. Generate stateless JSON Web Token (JWT).
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 4. Update audit logs (last login date).
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        log.info("User logged in successfully: {}", dto.getEmail());

        // 5. Build token response package.
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .user(UserMapper.toDTO(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return UserMapper.toDTO(user);
    }
}
