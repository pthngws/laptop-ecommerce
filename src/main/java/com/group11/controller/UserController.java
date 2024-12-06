package com.group11.controller;

import com.group11.entity.AddressEntity;
import com.group11.entity.UserEntity;
import com.group11.service.IJwtService;
import com.group11.service.IUserService;
import com.group11.service.impl.JwtServiceImpl;
import com.group11.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IJwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/my-info")
    public ResponseEntity<UserEntity> authenticatedUser(@RequestHeader("Authorization") String token) {
        // Loại bỏ prefix "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Giải mã JWT để lấy email
        String email = jwtService.extractClaim(token,claims -> claims.getSubject());

        // Tìm người dùng theo email
        UserEntity currentUser = userService.findByEmail(email);

        return ResponseEntity.ok(currentUser);
    }


    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> allUsers() {
        List<UserEntity> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/my-info/update")
    public void updateUser(@RequestHeader("Authorization") String token, @RequestBody UserEntity user) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtService.extractClaim(token,claims -> claims.getSubject());
        UserEntity currentUser = userService.findByEmail(email);
        currentUser.setName(user.getName());
        currentUser.setGender(user.getGender());
        currentUser.setPhone(user.getPhone());
        userService.save(currentUser);
    }
    @PostMapping("/my-info/update-address")
    public void updateAddress(@RequestHeader("Authorization") String token, @RequestBody AddressEntity address) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtService.extractClaim(token, claims -> claims.getSubject());
        UserEntity currentUser = userService.findByEmail(email);

        // Update the address
        currentUser.setAddress(address);
        userService.save(currentUser); // Save the user with the updated address
    }

    @PostMapping("/my-info/update-password")
    public void updatePassword(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> passwordData) {
        // Extract the token from the Authorization header
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove the "Bearer " prefix
        }

        // Extract the email (subject) from the JWT token
        String email = jwtService.extractClaim(token, claims -> claims.getSubject());

        // Find the user by email
        UserEntity currentUser = userService.findByEmail(email);

        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }

        // Extract the old password, new password, and confirm password from the request body
        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");

        // Validate the old password (not shown here but recommended)
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new RuntimeException("Old password does not match");
        }

        // Validate that the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("New password and confirmation do not match");
        }

        // Hash the new password and update the user's password
        currentUser.setPassword(passwordEncoder.encode(newPassword));

        // Save the user with the updated password
        userService.save(currentUser);
    }



}