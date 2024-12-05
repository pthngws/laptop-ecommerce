package com.group11.service.impl;

import com.group11.entity.AddressEntity;
import com.group11.entity.UserEntity;
import com.group11.model.LoginUserModel;
import com.group11.model.RegisterUserModel;
import com.group11.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity signup(RegisterUserModel input){
        UserEntity user = new UserEntity();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoleName("Customer");
        AddressEntity address = new AddressEntity();
        user.setAddress(address);
        return userRepository.save(user);
    }
    public UserEntity authenticate(LoginUserModel input){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userRepository.findByEmail(input.getEmail());
    }

    public void ChangePassword(String email, String password){
        UserEntity user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}