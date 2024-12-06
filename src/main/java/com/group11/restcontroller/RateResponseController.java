package com.group11.restcontroller;

import com.group11.dto.RateResponse;
import com.group11.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/rates")
public class RateResponseController {
    @Autowired
    private IRateService rateService;

    @GetMapping
    public ResponseEntity<List<RateResponse>> getAllRates() {
        List<RateResponse> rates = rateService.getAllRates();
        return ResponseEntity.ok(rates);
    }

    @PostMapping("/respond/{rateID}")
    public ResponseEntity<String> respondToRate(
            @PathVariable Long rateID,
            @RequestBody String response) {
        try {
            rateService.respondToRate(rateID, response);
            return ResponseEntity.ok("Phản hồi đánh giá thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    // Lấy chi tiết đánh giá
    @GetMapping("/{rateID}")
    public ResponseEntity<RateResponse> getRateDetail(@PathVariable Long rateID) {
        RateResponse rateDetail = rateService.getRateById(rateID);
        if (rateDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rateDetail);
    }
}
