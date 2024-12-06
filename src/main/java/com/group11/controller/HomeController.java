package com.group11.controller;

import com.group11.entity.ProductEntity;
import com.group11.service.IProductService;
import com.group11.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Random;

@Controller
public class HomeController {
    @Autowired
    IProductService productService = new ProductServiceImpl();

    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        // Kiểm tra xem khách đã có ID tạm trong session chưa
        String guestId = (String) session.getAttribute("GID");
        // Nếu chưa có ID, tạo ID tạm và lưu vào session
        if (guestId == null) {
            guestId = generateRandomNumberString(10);
            session.setAttribute("GID", guestId);
        }

        // Gửi ID tạm vào model để hiển thị lên trang
        model.addAttribute("GID", guestId);
        return "home"; // trả về view home (home.html hoặc home.jsp)
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String personalInfo() {
        return "profile";
    }

    @GetMapping("/inventory")
    public String inventory() {
        return "inventory";
    }

    @GetMapping("/recover")
    public String recover() {
        return "recover";
    }

    @GetMapping("/history")
    public String history() {
        return "my-account";
    }


    private String generateRandomNumberString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Thêm một chữ số ngẫu nhiên từ 0 đến 9
        }
        return sb.toString();
    }
}
