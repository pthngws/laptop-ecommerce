package com.group11.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/manufacturers")
public class AdminManufacturerController {

    @GetMapping
    public String showManufacturerPage() {
        // Trả về trang HTML chứa giao diện quản lý nhà sản xuất
        return "manufacturer"; // Trả về tên của file HTML (thường là manufacturer.html)
    }
}
