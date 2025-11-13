package com.skincare.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SkinAnalysis Entity
 * Stores AI-powered skin analysis results
 */
@Entity
@Table(name = "skin_analysis", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkinAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "analysis_result", columnDefinition = "TEXT")
    private String analysisResult;

    @Column(name = "skin_concerns", columnDefinition = "JSON")
    private String skinConcerns; // JSON array of detected concerns

    @Column(name = "confidence_score", precision = 5, scale = 2)
    private BigDecimal confidenceScore; // 0.00 to 100.00

    @Column(name = "skin_type_detected", length = 50)
    private String skinTypeDetected;

    @Column(name = "acne_detected")
    private Boolean acneDetected = false;

    @Column(name = "dark_spots_detected")
    private Boolean darkSpotsDetected = false;

    @Column(name = "wrinkles_detected")
    private Boolean wrinklesDetected = false;

    @Column(name = "dryness_detected")
    private Boolean drynessDetected = false;

    @Column(name = "redness_detected")
    private Boolean rednessDetected = false;

    @Column(columnDefinition = "TEXT")
    private String recommendations; // AI-generated recommendations

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relationship
    @OneToOne(mappedBy = "skinAnalysis", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Recommendation recommendation;
}
