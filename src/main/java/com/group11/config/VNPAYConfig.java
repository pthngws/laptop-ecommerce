package com.group11.config;

import com.group11.util.VNPayUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class VNPAYConfig {
    @Getter
    @Value("${payment.vnpay.url}")
    private String vnp_PayUrl;
    @Value("${payment.vnpay.returnUrl}")
    private String vnp_ReturnUrl;
    @Value("${payment.vnpay.tmnCode}")
    private String vnp_TmnCode ;
    @Getter
    @Value("${payment.vnpay.secretKey}")
    private String secretKey;
    @Value("${payment.vnpay.version}")
    private String vnp_Version;
    @Value("${payment.vnpay.command}")
    private String vnp_Command;
    @Value("${payment.vnpay.orderType}")
    private String orderType;

    public Map<String, String> getVNPayConfig(String orderId) {
        Map<String, String> vnpParamsMap = new HashMap<>();

        // Thông tin cấu hình VNPay
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toán đơn hàng:" + VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl + "?orderid=" + orderId);

        // Định dạng thời gian với múi giờ Việt Nam
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        // Lấy thời gian hiện tại theo múi giờ Việt Nam
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);

        // Tính thời gian hết hạn giao dịch (15 phút sau)
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnpParamsMap;
    }

}
