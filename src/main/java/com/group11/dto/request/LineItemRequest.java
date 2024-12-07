package com.group11.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemRequest {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private double total;
}
