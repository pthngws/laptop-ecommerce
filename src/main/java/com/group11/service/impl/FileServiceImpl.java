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
        String uploadDir = new File("src/main/resources/static/assets/").getAbsolutePath();

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Tạo đường dẫn file
        String filePath = uploadDir + File.separator + file.getOriginalFilename();

        // Lưu file vào thư mục
        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            throw new Exception("Error saving file: " + e.getMessage(), e);
        }

        // Trả về đường dẫn file tương đối
        return "/assets/" + file.getOriginalFilename();
    }

    @Override
    public MediaRateType determineMediaType(MultipartFile file) throws IllegalArgumentException {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();

        if (contentType.startsWith("image")) {
            return MediaRateType.IMAGE;
        } else if (contentType.startsWith("video")) {
            return MediaRateType.VIDEO;
        }

        // Kiểm tra qua đuôi file (extension)
        if (fileName != null) {
            String lowerCaseName = fileName.toLowerCase();
            if (lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg") || lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".gif")) {
                return MediaRateType.IMAGE;
            } else if (lowerCaseName.endsWith(".mp4") || lowerCaseName.endsWith(".avi") || lowerCaseName.endsWith(".mov")) {
                return MediaRateType.VIDEO;
            }
        }
        throw new IllegalArgumentException("Loại file không hợp lệ! Chỉ hỗ trợ IMAGE hoặc VIDEO.");
    }
}
