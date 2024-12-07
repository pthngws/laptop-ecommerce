package com.group11.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CartProductDTO {
    private Long productID;
    private String name;
    private String imageUrl;
    private int price;
}
