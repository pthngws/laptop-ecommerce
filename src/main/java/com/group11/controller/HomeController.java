package com.group11.controller;

import com.group11.entity.ProductEntity;
import com.group11.service.IProductService;
import com.group11.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @RequestMapping("/home")
    public String home(Model model) {
        List<ProductEntity> products = productService.findAll();
        model.addAttribute("products", products);
        return "MainHome";
    }

    @GetMapping("/signup")
    public String signup() {
        return "register";
    }

    @GetMapping("/login")
    public String home() {
        return "login";
    }

    @GetMapping("/personal-info")
    public String personalInfo() {
        return "personal-info";
    }

    @GetMapping("/inventory")
    public String inventory() {
        return "inventory";
    }
}
