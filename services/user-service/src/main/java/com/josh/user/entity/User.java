package com.josh.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * User Entity maps to the "USERS" table in the database.
 * 
 * 1. @Entity: Tells JPA/Hibernate that this class represents a table.
 * 2. @Table: Explicitly defines the table name in uppercase, following enterprise database standards.
 */
@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique Identifier for the user.
     * GenerationType.IDENTITY delegates ID generation to the database's auto-increment feature.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    /**
     * Email is unique and is used as the primary login credential.
     * @Email validation ensures input conforms to standard email formats.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    /**
     * Securely hashed password.
     * IMPORTANT: Never store passwords in plain text. We hash it using BCrypt.
     * A BCrypt hash always yields a string of ~60 characters, so we ensure the DB length is sufficient.
     */
    @NotBlank(message = "Password is required")
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    /**
     * Enum representation of User roles.
     * 
     * IMPORTANT DESIGN DETAIL:
     * We use EnumType.STRING instead of the default EnumType.ORDINAL.
     * - ORDINAL saves enum index as integers (0, 1, 2). If you change the enum order later, all existing DB rows will mismatch!
     * - STRING saves the literal text (e.g., "ROLE_USER"). It is safer, easier to read, and resilient to code changes.
     */
    @NotNull(message = "User role is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private Role role;

    @Column(name = "IS_VERIFIED", nullable = false)
    private boolean verified = false;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * PrePersist lifecycle callback.
     * Executed automatically by Hibernate right before saving a new record.
     * Perfect for setting initial audit fields like 'createdAt'.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.verified = false; // Initial users require registration verification
    }

    /**
     * PreUpdate lifecycle callback.
     * Executed automatically by Hibernate right before updating an existing record.
     * Perfect for refreshing 'updatedAt'.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
