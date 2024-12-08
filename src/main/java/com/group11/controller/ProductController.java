package com.group11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    @GetMapping("/products")
    public String products()
    {
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productsID()
    {
        return "productDetails";
    }


}
