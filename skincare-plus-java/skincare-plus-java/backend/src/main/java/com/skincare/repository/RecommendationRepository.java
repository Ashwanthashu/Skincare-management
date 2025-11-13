package com.skincare.repository;

import com.skincare.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Recommendation Repository
 */
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    List<Recommendation> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Recommendation> findBySkinAnalysisId(Long analysisId);

    @Query("SELECT r FROM Recommendation r WHERE r.user.id = :userId ORDER BY r.createdAt DESC LIMIT 1")
    Optional<Recommendation> findLatestByUserId(Long userId);

    Long countByUserId(Long userId);
}
