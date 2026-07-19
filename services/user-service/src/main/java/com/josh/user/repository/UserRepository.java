package com.josh.user.repository;

import com.josh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository interface handles all database operations for the User entity.
 * 
 * 1. @Repository: Marks this as a Spring Data access component.
 * 2. JpaRepository<User, Long>: Inherits built-in CRUD operations (save, findById, delete, etc.)
 *    where 'User' is the managed Entity type and 'Long' is the ID type.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query derivation by method name.
     * Spring Data JPA automatically parses this method signature and generates the SQL:
     * "SELECT * FROM users WHERE email = ?"
     * 
     * We wrap the response in an Optional to handle cases where the email is not registered,
     * which prevents NullPointerExceptions in our service layer.
     */
    Optional<User> findByEmail(String email);
}
