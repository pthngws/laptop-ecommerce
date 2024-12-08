package com.group11.service;

import com.group11.dto.request.PromotionRequest;
import com.group11.entity.PromotionEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPromotionService {
    public List<PromotionEntity> getPromotions();
    public Page<PromotionEntity> getAllPromotions(int page, int size);
    public List<PromotionEntity> getValidPromotions();
    public PromotionEntity getPromotionById(Long id);
    public PromotionEntity getPromotionByCode(String code);
    public PromotionEntity applyPromotionByCode(String code);
    public PromotionEntity createPromotion(PromotionRequest promotion);
    public PromotionEntity updatePromotion(PromotionRequest promotion, Long promotionID);
    public void deletePromotion(Long id);

}
