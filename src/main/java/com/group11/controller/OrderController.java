package com.group11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/mua")
    public String mua() {
        return "checkout";
    }
}
