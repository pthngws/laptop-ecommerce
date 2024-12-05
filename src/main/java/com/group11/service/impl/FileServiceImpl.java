package com.group11.service.impl;

import com.group11.entity.MediaRateType;
import com.group11.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileServiceImpl implements IFileService {
    @Override
    public String saveFile(MultipartFile file) throws Exception {
        String filePath = "/path/to/media/" + file.getOriginalFilename();
        file.transferTo(new File(filePath)); // Lưu file vào ổ đĩa hoặc Cloud Storage
        return filePath;
    }

    @Override
    public MediaRateType determineMediaType(MultipartFile file) throws IllegalArgumentException {
        String contentType = file.getContentType();
        if (contentType.startsWith("image")) {
            return MediaRateType.IMAGE;
        } else if (contentType.startsWith("video")) {
            return MediaRateType.VIDEO;
        }
        throw new IllegalArgumentException("Loại file không hợp lệ! Chỉ hỗ trợ IMAGE hoặc VIDEO.");
    }
}
