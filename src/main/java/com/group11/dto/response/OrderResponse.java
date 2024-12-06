package com.group11.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private String shippingStatus;
    private double totalAmount;
    private List<LineItemResponse> items;
}
