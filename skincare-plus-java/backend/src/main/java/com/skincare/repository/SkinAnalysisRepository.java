package com.skincare.repository;

import com.skincare.model.SkinAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SkinAnalysis Repository
 * Data access layer for SkinAnalysis entity
 */
@Repository
public interface SkinAnalysisRepository extends JpaRepository<SkinAnalysis, Long> {

    /**
     * Find all analyses by user ID
     */
    List<SkinAnalysis> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Find analyses by user within date range
     */
    @Query("SELECT sa FROM SkinAnalysis sa WHERE sa.user.id = :userId AND sa.createdAt BETWEEN :startDate AND :endDate ORDER BY sa.createdAt DESC")
    List<SkinAnalysis> findByUserAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find analyses with acne detected
     */
    List<SkinAnalysis> findByAcneDetectedTrue();

    /**
     * Find analyses by skin type
     */
    List<SkinAnalysis> findBySkinTypeDetected(String skinType);

    /**
     * Count analyses by user
     */
    Long countByUserId(Long userId);

    /**
     * Find latest analysis by user
     */
    @Query("SELECT sa FROM SkinAnalysis sa WHERE sa.user.id = :userId ORDER BY sa.createdAt DESC LIMIT 1")
    SkinAnalysis findLatestByUserId(@Param("userId") Long userId);

    /**
     * Find analyses with high confidence scores
     */
    @Query("SELECT sa FROM SkinAnalysis sa WHERE sa.confidenceScore >= :minScore ORDER BY sa.confidenceScore DESC")
    List<SkinAnalysis> findByHighConfidence(@Param("minScore") Double minScore);

    /**
     * Get analysis statistics
     */
    @Query("SELECT COUNT(sa), AVG(sa.confidenceScore), sa.skinTypeDetected FROM SkinAnalysis sa GROUP BY sa.skinTypeDetected")
    List<Object[]> getAnalysisStatistics();
}
