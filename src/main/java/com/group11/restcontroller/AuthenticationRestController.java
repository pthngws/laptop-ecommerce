package com.group11.restcontroller;

import com.group11.entity.UserEntity;
import com.group11.model.LoginResponse;
import com.group11.model.LoginUserModel;
import com.group11.model.RegisterUserModel;
import com.group11.service.IAuthenticationService;
import com.group11.service.IJwtService;
import com.group11.service.IUserService;
import com.group11.service.impl.EmailServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {
    @Autowired
    IAuthenticationService authenticationService;

    @Autowired
    IUserService userService;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private EmailServiceImpl emailService; // Inject EmailServiceImpl

    private Map<String, String> otpStorage = new HashMap<>(); // Store OTP temporarily in memory

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterUserModel registerUser) {
        // Check if the OTP is valid before registering the user
        String otp = registerUser.getOtp();
        String storedOtp = otpStorage.get(registerUser.getEmail());

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.badRequest().body("OTP Không hợp lệ");
        }

        // Proceed with user registration if OTP is valid
        authenticationService.signup(registerUser);
        otpStorage.remove(registerUser.getEmail()); // Clear OTP after registration
        return ResponseEntity.ok("Đăng ký thành công!");
    }



    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        // Generate OTP
        String otp = emailService.generateOtp();

        // Create email details
        String subject = "OTP code";
        String message = "Mã OTP của bạn là: " + otp;

        // Send email with OTP
        emailService.sendEmail(email, subject, message);

        // Store OTP temporarily for validation during registration
        otpStorage.put(email, otp);

        // Return a success response
        return ResponseEntity.ok("OTP đã được gửi đến email của bạn!");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginUserModel loginUser) {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUser);
        LoginResponse loginResponse = new LoginResponse();
        if(!authenticatedUser.getActive()) {
            return ResponseEntity.badRequest().body("Tài khoản đã bị khóa!");
        }
        String jwtToken = jwtService.generateToken(authenticatedUser);

        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Tạo cookie để xóa JWT
        Cookie cookie = new Cookie("jwtToken", null);  // JWT token trong cookie
        cookie.setHttpOnly(true);  // Giúp bảo vệ cookie khỏi việc bị truy cập từ JavaScript
        cookie.setSecure(true);  // Nếu sử dụng HTTPS
        cookie.setPath("/");  // Đảm bảo cookie được xóa cho tất cả các route
        cookie.setMaxAge(0);  // Xóa cookie ngay lập tức
        response.addCookie(cookie);

        return "Đăng xuất thành công!";
    }

    @PostMapping("/send-otp-reset")
    public ResponseEntity<String> sendOtpForPasswordReset(@RequestParam String email) {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        UserEntity user =userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại trong hệ thống.");
        }

        // Tạo OTP
        String otp = emailService.generateOtp();

        // Tạo nội dung email
        String subject = "OTP CODE";
        String message = "Mã OTP của bạn là: " + otp;

        // Gửi email với OTP
        emailService.sendEmail(email, subject, message);

        // Lưu OTP tạm thời vào Map
        otpStorage.put(email, otp);

        // Trả về phản hồi thành công
        return ResponseEntity.ok("OTP đã được gửi đến email của bạn.");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String otp,
                                                @RequestParam String newPassword) {
        // Kiểm tra OTP
        String storedOtp = otpStorage.get(email);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc đã hết hạn.");
        }

        // Tìm người dùng bằng email
        UserEntity user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại.");
        }
        authenticationService.ChangePassword(email,newPassword);

        // Xóa OTP sau khi sử dụng
        otpStorage.remove(email);

        return ResponseEntity.ok("Mật khẩu của bạn đã được đặt lại thành công.");
    }

}
