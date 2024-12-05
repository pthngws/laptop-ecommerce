package com.group11.controller;

import com.group11.entity.UserEntity;
import com.group11.model.LoginResponse;
import com.group11.model.LoginUserModel;
import com.group11.model.RegisterUserModel;
import com.group11.service.impl.AuthenticationService;
import com.group11.service.impl.JwtService;
import com.group11.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

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
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        // Proceed with user registration if OTP is valid
        authenticationService.signup(registerUser);
        otpStorage.remove(registerUser.getEmail()); // Clear OTP after registration
        return ResponseEntity.ok("Registration successful!");
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        // Generate OTP
        String otp = emailService.generateOtp();

        // Create email details
        String subject = "Your OTP Code for Registration";
        String message = "Your OTP code is: " + otp;

        // Send email with OTP
        emailService.sendEmail(email, subject, message);

        // Store OTP temporarily for validation during registration
        otpStorage.put(email, otp);

        // Return a success response
        return ResponseEntity.ok("OTP sent to your email.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUser);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
