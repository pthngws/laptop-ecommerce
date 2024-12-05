package com.group11.service;

import com.group11.dto.RateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IRateService {
    void addRate(RateRequest rateRequest, MultipartFile[] files) throws Exception;
}
