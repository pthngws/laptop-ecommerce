package com.group11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponse {
    private Long rateID;
    private String content;
    private int rate;
    private String response;
    private String username;
    private String productName;
    private List<String> mediaUrls;
}
