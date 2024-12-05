package com.group11.service;

import com.group11.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService extends UserDetailsService {

    UserEntity findByEmail(String email);
    void save(UserEntity user);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    // Phương thức yêu cầu trả về UserDetails
}
