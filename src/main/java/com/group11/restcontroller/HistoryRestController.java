package com.group11.restcontroller;


import com.group11.dto.response.CheckoutResponse;
import com.group11.entity.OrderEntity;
import com.group11.entity.UserEntity;
import com.group11.service.IHistoryService;
import com.group11.service.IJwtService;
import com.group11.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryRestController {
    @Autowired
    private IHistoryService historyService;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private UserServiceImpl userService;

    // Phương thức GET để xem lịch sử đơn hàng
    @GetMapping
    public ResponseEntity<?> viewOrderHistory(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bạn cần đăng nhập để xem lịch sử đơn hàng.");
        }

        token = token.substring(7); // Loại bỏ "Bearer " để lấy JWT thực tế

        String email = jwtService.extractClaim(token, claims -> claims.getSubject());
        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User không tồn tại.");
        }

        Long userID = user.getUserID();
        List<OrderEntity> orders = historyService.getPurchaseHistory(userID);

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Bạn chưa mua đơn hàng nào.");
        }

        return ResponseEntity.ok(orders);
    }
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@RequestHeader("Authorization") String token,
                                         @PathVariable Long orderId,
                                         @RequestParam String accountNumber,
                                         @RequestParam String accountName,
                                         @RequestParam String bankName) {

        System.out.println("Cancel request received for orderId: " + orderId);
        System.out.println("Account details: " + accountNumber + ", " + accountName + ", " + bankName);




        // Gọi phương thức hủy đơn hàng từ service
        historyService.cancelOrder(orderId, accountNumber, accountName, bankName);

        return ResponseEntity.ok("Đơn hàng đã được hủy thành công.");
    }

    // Endpoint xem chi tiết đơn hàng
    @GetMapping("/details/{orderId}")
    public ResponseEntity<?> getOrderDetails(@RequestHeader("Authorization") String token, @PathVariable Long orderId) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bạn cần đăng nhập để xem chi tiết đơn hàng.");
        }

        token = token.substring(7); // Loại bỏ "Bearer " để lấy JWT thực tế
        String email = jwtService.extractClaim(token, claims -> claims.getSubject());
        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User không tồn tại.");
        }

        CheckoutResponse orderDetails = historyService.getOrderDetails(orderId);

        if (!orderDetails.getEmail().equals(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền xem đơn hàng này.");
        }

        return ResponseEntity.ok(orderDetails);
    }


}
