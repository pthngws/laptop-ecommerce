package com.group11.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ProductDetailDTO {
    @JsonProperty("RAM")
    private String RAM;
    @JsonProperty("CPU")
    private String CPU;
    @JsonProperty("GPU")
    private String GPU;
    private String monitor;
    private String charger;
    private String disk;
    private String connect;

    private List<ImageItemDTO> images;

}
