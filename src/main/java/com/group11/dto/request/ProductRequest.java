package com.group11.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private int price;
    private String category;
    private String manufacturer;
    private ProductDetailRequest detail;
    private List<String> imageUrls;
    @Data
    public static class ProductDetailRequest {
        private String RAM;
        private String CPU;
        private String GPU;
    }

}
