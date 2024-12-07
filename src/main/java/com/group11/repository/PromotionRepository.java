package com.group11.repository;

import com.group11.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
    public PromotionEntity findByPromotionCode(String promotionCode);
    @Query("SELECT p FROM PromotionEntity p WHERE p.remainingUses > 0 AND p.validTo >= CURRENT_TIMESTAMP")
    List<PromotionEntity> findValidPromotions();
}
