package com.group11.restcontroller;


import com.group11.dto.request.PromotionRequest;
import com.group11.entity.PromotionEntity;
import com.group11.repository.PromotionRepository;
import com.group11.service.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionRestController {
    @Autowired
    private IPromotionService promotionService;

    @GetMapping
    public ResponseEntity<Page<PromotionEntity>> getPromotions(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Page<PromotionEntity> promotions = promotionService.getAllPromotions(page, size);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/valid")
    public ResponseEntity<List<PromotionEntity>> getValidPromotions() {
        List<PromotionEntity> validPromotions = promotionService.getValidPromotions();
        return ResponseEntity.ok(validPromotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionEntity> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PromotionEntity> getPromotionByCode(@PathVariable String code) {
        PromotionEntity promotion = promotionService.getPromotionByCode(code);
        if (promotion != null)
            return ResponseEntity.ok(promotion);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/apply/{code}")
    public ResponseEntity<PromotionEntity> applyPromotionByCode(@PathVariable String code) {
        PromotionEntity promotion = promotionService.applyPromotionByCode(code);
        if (promotion != null)
            return ResponseEntity.ok(promotion);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PromotionEntity> createPromotion(@RequestBody PromotionRequest promotion) {
        return ResponseEntity.ok(promotionService.createPromotion(promotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionEntity> updatePromotion(@PathVariable Long id, @RequestBody PromotionRequest promotion) {
        return ResponseEntity.ok(promotionService.updatePromotion(promotion, id));
    }


    @DeleteMapping("/{id}")
    public void deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
    }



}
