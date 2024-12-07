package com.group11.restcontroller;

import com.group11.dto.ResponseObject;
import com.group11.dto.PaymentDTO;
import com.group11.service.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentestController {
    @Autowired
    private final IPaymentService paymentService;

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO> bankPay(HttpServletRequest request) {
        return new ResponseObject<  >(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public void bankPayCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String status = request.getParameter("vnp_ResponseCode");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String bankCode = request.getParameter("vnp_BankCode");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        long amount = Long.parseLong(request.getParameter("vnp_Amount"))/100;

        String payDate = request.getParameter("vnp_PayDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse(payDate, formatter);

        Long orderId = Long.parseLong(request.getParameter("orderid"));

        if (status.equals("00")) {
            paymentService.handlePayBank(transactionNo,bankCode,transactionStatus,localDateTime,(int)amount, orderId);
            response.getWriter().write(
                    "<script>" +
                            "alert('Đơn hàng đã thanh toán thành công');" +
                            "window.location.href = '/history';"+
                            "</script>"
            );
            //return ResponseEntity.ok(Map.of("status", "success"));
        } else {
            response.getWriter().write(
                    "<script>" +
                         "alert('Thanh toán thất bại. Vui lòng thử lại!');" + // Hiển thị popup lỗi//
                            "window.location.href = '/purchase/payment?orderId="+orderId +"&amount=" + amount +
                    "</script>"
            );
            //return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "Số tiền thanh toán không đủ"));
        }
    }
}

