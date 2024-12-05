package com.group11.service;

import com.group11.dto.EmailDetail;

public interface IEmailService {
    String sendEmailConfirmCancelOrder(EmailDetail emailDetail);
    void sendEmail(String to, String subject, String message);
    public void sendInvoice(EmailDetail detail);

    String generateOtp();
}
