package com.group11.service.impl;

import com.group11.dto.RateRequest;
import com.group11.entity.*;
import com.group11.repository.MediaRateRepository;
import com.group11.repository.ProductRepository;
import com.group11.repository.RateRepository;
import com.group11.repository.UserRepository;
import com.group11.service.IFileService;
import com.group11.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RateServiceImpl  implements IRateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private MediaRateRepository mediaRateRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IFileService fileService;

    @Override
    public void addRate(RateRequest rateRequest, MultipartFile[] files) throws Exception {
        UserEntity user = userRepository.findById(rateRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng!"));
        ProductEntity product = productRepository.findById(rateRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm!"));

        // Tạo RateEntity
        RateEntity rate = new RateEntity();
        rate.setUser(user);
        rate.setProduct(product);
        rate.setContent(rateRequest.getContent());
        rate.setRate(rateRequest.getRate());
        rateRepository.save(rate);

        // Lưu các tệp (nếu có)
        if (files != null) {
            for (MultipartFile file : files) {
                // Lưu tệp và xác định loại tệp
                String mediaUrl = fileService.saveFile(file);
                MediaRateType mediaType = fileService.determineMediaType(file);

                // Tạo MediaRateEntity
                MediaRateEntity media = new MediaRateEntity();
                media.setMediaUrl(mediaUrl);
                media.setMediaType(mediaType);
                media.setRate(rate);
                mediaRateRepository.save(media);
            }
        }
    }
}
