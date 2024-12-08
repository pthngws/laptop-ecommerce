package com.group11.service.impl;

import com.group11.dto.request.PromotionRequest;
import com.group11.entity.PromotionEntity;
import com.group11.repository.PromotionRepository;
import com.group11.service.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    PromotionRepository promotionRepository;

    @Override
    public List<PromotionEntity> getPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Page<PromotionEntity> getAllPromotions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return promotionRepository.findAll(pageable);
    }

    @Override
    public List<PromotionEntity> getValidPromotions() {
        return promotionRepository.findValidPromotions();
    }

    @Override
    public PromotionEntity getPromotionById(Long id) {
        return promotionRepository.findById(id).get();
    }
    @Override
    public PromotionEntity getPromotionByCode(String code) {
        return promotionRepository.findByPromotionCode(code);
    }

    @Override
    public PromotionEntity applyPromotionByCode(String code) {
        PromotionEntity promotion = promotionRepository.findByPromotionCode(code);
        promotion.setRemainingUses(promotion.getRemainingUses() - 1);
        return promotionRepository.save(promotion);
    }
    @Override
    public PromotionEntity createPromotion(PromotionRequest promotion) {
        PromotionEntity promotionEntity = new PromotionEntity();

        promotionEntity.setPromotionCode(promotion.getPromotionCode());
        promotionEntity.setDescription(promotion.getDescription());
        promotionEntity.setValidFrom(promotion.getValidFrom());
        promotionEntity.setValidTo(promotion.getValidTo());
        promotion.setDiscountAmount(promotion.getDiscountAmount());
        promotion.setRemainingUses(promotion.getRemainingUses());
        return promotionRepository.save(promotionEntity);
    }
    @Override
    public PromotionEntity updatePromotion(PromotionRequest promotion, Long promotionID) {
        PromotionEntity promotionEntity = promotionRepository.findById(promotionID).get();

        promotionEntity.setPromotionCode(promotion.getPromotionCode());
        promotionEntity.setDescription(promotion.getDescription());
        promotionEntity.setValidFrom(promotion.getValidFrom());
        promotionEntity.setValidTo(promotion.getValidTo());
        promotionEntity.setDiscountAmount(promotion.getDiscountAmount());
        promotionEntity.setRemainingUses(promotion.getRemainingUses());

        return promotionRepository.save(promotionEntity);
    }
    @Override
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }
}
