package com.group11.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private String status;  // Trạng thái của sản phẩm (Ví dụ: "AVAILABLE", "OUT_OF_STOCK")
    private Long categoryId;  // ID của danh mục sản phẩm
    private Long manufacturerId;  // ID của nhà sản xuất

    private String RAM;
    private String CPU;
    private String GPU;
    private String monitor;
    private String charger;
    private String disk;
    private String connect;
    private List<String> images;
    public ProductDTO(Long id,String name, int price,List<String> images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;

    }
}
