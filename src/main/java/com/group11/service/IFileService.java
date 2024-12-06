package com.group11.service;

import com.group11.entity.MediaRateType;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String saveFile(MultipartFile file) throws Exception;
    MediaRateType determineMediaType(MultipartFile file) throws IllegalArgumentException;
}
