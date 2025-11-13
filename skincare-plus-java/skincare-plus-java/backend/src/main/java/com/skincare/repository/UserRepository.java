package com.skincare.repository;

import com.skincare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository
 * Data access layer for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if username exists
     */
    Boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    Boolean existsByEmail(String email);

    /**
     * Find user by username or email
     */
    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    Optional<User> findByUsernameOrEmail(@Param("identifier") String identifier);

    /**
     * Find all active users
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * Find users by skin type
     */
    List<User> findBySkinType(String skinType);

    /**
     * Find users by role
     */
    List<User> findByRole(User.Role role);

    /**
     * Custom query to find users with most analyses
     */
    @Query("SELECT u FROM User u LEFT JOIN u.skinAnalyses sa GROUP BY u.id ORDER BY COUNT(sa) DESC")
    List<User> findUsersWithMostAnalyses();

    /**
     * Count total active users
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    Long countActiveUsers();
}
