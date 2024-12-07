package com.group11.service.impl;

import com.group11.entity.AddressEntity;
import com.group11.entity.UserEntity;
import com.group11.model.LoginUserModel;
import com.group11.model.RegisterUserModel;
import com.group11.repository.UserRepository;
import com.group11.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserEntity signup(RegisterUserModel input){
        UserEntity user = new UserEntity();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoleName("Customer");
        AddressEntity address = new AddressEntity();
        address.setCommune("Không có");
        address.setCountry("Không có");
        address.setProvince("Không có");
        address.setDistrict("Không có");
        address.setOther("Không có");
        user.setAddress(address);
        return userRepository.save(user);
    }
    @Override
    public UserEntity authenticate(LoginUserModel input){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userRepository.findByEmail(input.getEmail());
    }
    @Override
    public void ChangePassword(String email, String password){
        UserEntity user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}