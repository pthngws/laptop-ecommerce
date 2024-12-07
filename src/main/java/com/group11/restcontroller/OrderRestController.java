package com.group11.restcontroller;

import com.group11.dto.request.LineItemRequest;
import com.group11.dto.response.OrderResponse;
import com.group11.entity.AddressEntity;
import com.group11.entity.ProductEntity;
import com.group11.service.IJwtService;
import com.group11.service.IOrderService;
import com.group11.service.IProductService;
import com.group11.dto.response.CheckoutResponse;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IJwtService jwtService;
    @Autowired
    private IProductService productService;

    @GetMapping
    public Page<OrderResponse> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getAllOrders(page, size);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{id}")
    public Page<OrderResponse> getOrdersByUserId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderService.getAllOrdersByUserId(id, page, size);
    }

    @GetMapping("/search/{keyword}")
    public Page<OrderResponse> searchOrders(@PathVariable String keyword,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return orderService.searchOrders(keyword, page, size);
    }


    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkoutOrders(@RequestHeader("Authorization") String token,
                                                                @RequestBody List<LineItemRequest> cartItems){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = jwtService.extractAllClaims(token);
        String name = claims.get("name", String.class);
        String phone = claims.get("phone", String.class);
        String email = claims.getSubject();
        Map<String, Object> address = claims.get("address", Map.class);
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressID(((Number) address.get("addressID")).longValue());
        addressEntity.setCountry((String) address.get("country"));
        addressEntity.setProvince((String) address.get("province"));
        addressEntity.setDistrict((String) address.get("district"));
        addressEntity.setCommune((String) address.get("commune"));

        AtomicInteger total = new AtomicInteger();
        List<LineItemRequest> processedCartItems = cartItems.stream()
                .map(item -> {
                    // Giả sử bạn muốn kiểm tra thông tin sản phẩm từ database
                    ProductEntity product = productService.findProductById(item.getProductId()).get();
                    if (product == null) {
                        throw new IllegalArgumentException("Sản phẩm không tồn tại: " + item.getProductId());
                    }

                    // Tính giá trị mỗi sản phẩm
                    item.setProductName(product.getName());
                    item.setPrice(product.getPrice());
                    item.setTotal(item.getQuantity() * product.getPrice());
                    total.addAndGet(item.getQuantity() * product.getPrice());
                    return item;
                })
                .collect(Collectors.toList());

        // Tạo phản hồi
        CheckoutResponse response = new CheckoutResponse();
        response.setName(name);
        response.setEmail(email);
        response.setAddress(addressEntity);
        response.setCartItems(processedCartItems);
        response.setTotal(total.get());
        // Trả lại danh sách đã xử lý
        return ResponseEntity.ok(response);
    }

}

