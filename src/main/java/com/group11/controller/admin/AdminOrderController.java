package com.group11.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminOrderController {
    @GetMapping("/orders")
    public String index() {
        return "order-list";
    }
}
