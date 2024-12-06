package com.group11.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {
    @GetMapping
    public String index1() {
        return "customer-list";
    }

    @GetMapping("/{id}")
    public String index2(@PathVariable int id) {
        return "customer-details";
    }
}
