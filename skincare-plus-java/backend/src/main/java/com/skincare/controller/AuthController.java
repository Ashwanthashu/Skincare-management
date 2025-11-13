package com.skincare.controller;

import com.skincare.dto.*;
import com.skincare.model.User;
import com.skincare.repository.UserRepository;
import com.skincare.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles user registration, login, and authentication
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Register new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Username is already taken!"));
        }

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Email is already registered!"));
        }

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .skinType(request.getSkinType())
                .role(User.Role.USER)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        // Generate JWT token
        String token = jwtTokenProvider.generateTokenFromUsername(savedUser.getUsername());

        JwtResponse jwtResponse = new JwtResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().toString()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .success(true)
                .message("User registered successfully!")
                .data(jwtResponse)
                .build());
    }

    /**
     * User login
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(authentication);

            // Get user details
            User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            JwtResponse jwtResponse = new JwtResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().toString()
            );

            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .message("Login successful!")
                    .data(jwtResponse)
                    .build());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid username or password!"));
        }
    }

    /**
     * Get current user profile
     * GET /api/auth/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "User not authenticated"));
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .skinType(user.getSkinType())
                .role(user.getRole().toString())
                .createdAt(user.getCreatedAt())
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Profile retrieved successfully")
                .data(userDTO)
                .build());
    }

    /**
     * Logout user
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful!"));
    }

    /**
     * Check if username is available
     * GET /api/auth/check-username
     */
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        Boolean available = !userRepository.existsByUsername(username);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(available ? "Username is available" : "Username is taken")
                .data(available)
                .build());
    }

    /**
     * Check if email is available
     * GET /api/auth/check-email
     */
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        Boolean available = !userRepository.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(available ? "Email is available" : "Email is already registered")
                .data(available)
                .build());
    }
}

// Additional DTO classes
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
class RegisterRequest {
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 3, max = 50)
    private String username;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Email
    private String email;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 6)
    private String password;

    private String fullName;
    private String phone;
    private String skinType;
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String role;

    public JwtResponse(String token, Long id, String username, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
class ApiResponse {
    private Boolean success;
    private String message;
    private Object data;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String skinType;
    private String role;
    private java.time.LocalDateTime createdAt;
}
