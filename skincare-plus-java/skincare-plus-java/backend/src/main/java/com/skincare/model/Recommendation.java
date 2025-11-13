package com.skincare.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Recommendation Entity
 * Stores personalized skincare recommendations
 */
@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id")
    @ToString.Exclude
    private SkinAnalysis skinAnalysis;

    @Column(name = "morning_routine", columnDefinition = "TEXT")
    private String morningRoutine;

    @Column(name = "evening_routine", columnDefinition = "TEXT")
    private String eveningRoutine;

    @Column(name = "product_recommendations", columnDefinition = "JSON")
    private String productRecommendations; // JSON array of products

    @Column(columnDefinition = "TEXT")
    private String dietAdvice;

    @Column(columnDefinition = "TEXT")
    private String lifestyleAdvice;

    @Column(name = "dos", columnDefinition = "TEXT")
    private String dos; // Things to do

    @Column(name = "donts", columnDefinition = "TEXT")
    private String donts; // Things to avoid

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
