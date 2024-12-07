package com.group11.dto.response;

import com.group11.dto.request.LineItemRequest;
import com.group11.entity.AddressEntity;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse {
    private String name;
    private String email;
    private AddressEntity address;
    private List<LineItemRequest> cartItems;
    private int total;
}
