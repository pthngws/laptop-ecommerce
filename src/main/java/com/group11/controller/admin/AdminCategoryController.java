package com.group11.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @GetMapping
    public String showCategoryPage() {
        // Trả về trang HTML chứa giao diện quản lý nhà sản xuất
        return "category"; // Trả về tên của file HTML (thường là manufacturer.html)
    }
}
