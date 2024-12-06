package com.group11.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/promotions")
public class AdminPromotionController {
    @GetMapping
    public String getListPromotions() {
        return "promotion";
    }

    @GetMapping("/add")
    public String createPromotion() {
        return "promotion-add";
    }
}
