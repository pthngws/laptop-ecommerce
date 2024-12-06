package com.group11.service;

import com.group11.dto.RateRequest;
import com.group11.dto.RateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRateService {
    void addRate(RateRequest rateRequest, MultipartFile[] files) throws Exception;
    void respondToRate(Long rateID, String response) throws Exception;
    RateResponse getRateById(Long rateID);
    List<RateResponse> getAllRates();
}
