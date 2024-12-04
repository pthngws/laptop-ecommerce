package com.group11.service;

import com.group11.dto.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IPaymentService {
    public PaymentDTO createVnPayPayment(HttpServletRequest request);
    void handlePayBank(String transactionNo, String bankCode, String transactionStatus, LocalDateTime localDateTime, int amount, Long orderId);

}
