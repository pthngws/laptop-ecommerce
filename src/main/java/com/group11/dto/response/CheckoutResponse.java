package com.group11.dto.response;

import com.group11.dto.request.LineItemRequest;
import com.group11.entity.AddressEntity;
import com.group11.entity.OrderShippingStatus;
import com.group11.entity.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse {
    private String name;
    private String email;
    private String phone;
    private String note;
    private AddressEntity address;
    private List<LineItemRequest> cartItems;
    private int total;

    private Long orderId;  // Mã đơn hàng
    private LocalDateTime orderDate;  // Ngày đặt
    private OrderShippingStatus shippingStatus;  // Trạng thái giao hàng
    private PaymentStatus paymentStatus;
}
