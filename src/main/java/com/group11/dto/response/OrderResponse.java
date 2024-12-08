package com.group11.dto.response;

import com.group11.entity.AddressEntity;
import com.group11.entity.PaymentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String name;
    private String phone;
    private AddressEntity address;
    private LocalDateTime orderDate;
    private LocalDateTime receiveDate;
    private String shippingStatus;
    private String paymentStatus;
    private String note;
    private double totalAmount;
    private List<LineItemResponse> items;
}
