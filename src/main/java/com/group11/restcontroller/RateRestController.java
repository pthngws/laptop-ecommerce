package com.group11.restcontroller;

import com.group11.dto.RateRequest;
import com.group11.service.IJwtService;
import com.group11.service.IRateService;
import com.group11.entity.UserEntity;
import com.group11.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/rates")
public class RateRestController {

    @Autowired
    private IRateService rateService;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/add")
    public ResponseEntity<String> addRate(
            @RequestHeader("Authorization") String token,
            @ModelAttribute RateRequest rateRequest,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bạn cần đăng nhập để đánh giá sản phẩm.");
        }

        token = token.substring(7);
        String email = jwtService.extractClaim(token, claims -> claims.getSubject());
        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Người dùng không tồn tại.");
        }

        rateRequest.setUserId(user.getUserID());

        try {
            rateService.addRate(rateRequest, files);
            return ResponseEntity.ok("Đánh giá đã được thêm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }

}
