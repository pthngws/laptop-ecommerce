package com.group11.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer/rates")
public class RateController {

    @GetMapping
    public String showRateProductPage(@RequestParam("productId") String productId, Model model) {
        // Thêm productId vào model để truyền tới view
        model.addAttribute("productId", productId);
        return "rateproduct"; // Tên file HTML là rateproduct.html
    }
}
