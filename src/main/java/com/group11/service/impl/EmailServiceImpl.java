package com.group11.service.impl;

import com.group11.dto.EmailDetail;
import com.group11.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.SecureRandom;

@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendEmailConfirmCancelOrder(EmailDetail detail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(detail.getRecipient());
            helper.setSubject(detail.getSubject());
            helper.setText(detail.getMsgBody());

            // Kiểm tra và thêm tệp đính kèm (nếu có)
            if (detail.getAttachment() != null && !detail.getAttachment().isEmpty()) {
                File attachment = new File(detail.getAttachment());
                helper.addAttachment(attachment.getName(), attachment);
            }

            mailSender.send(message);
            return "Email thông báo hủy đơn hàng đã được gửi thành công!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Lỗi khi gửi email thông báo hủy đơn hàng!";
        }
    }
    @Override
    public void sendInvoice(EmailDetail detail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("buiducthang13022004@gmail.com");
        simpleMailMessage.setTo(detail.getRecipient());
        simpleMailMessage.setSubject(detail.getSubject());
        simpleMailMessage.setText(detail.getMsgBody());

        this.mailSender.send(simpleMailMessage);
    }


    @Override
    public void sendEmail(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("buiducthang13022004@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int digit = random.nextInt(10); // Lấy một số ngẫu nhiên từ 0 đến 9
            otp.append(digit);
        }
        return otp.toString();
    }
}

