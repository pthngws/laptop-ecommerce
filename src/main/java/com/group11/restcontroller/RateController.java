package com.group11.restcontroller;

import com.group11.dto.RateRequest;
import com.group11.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/rates")
public class RateController {

    @Autowired
    private IRateService rateService;

    @PostMapping("/add")
    public ResponseEntity<String> addRate(
            @ModelAttribute RateRequest rateRequest,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {

        try {
            rateService.addRate(rateRequest, files); // Gọi phương thức từ interface
            return ResponseEntity.ok("Đánh giá đã được thêm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }
}
