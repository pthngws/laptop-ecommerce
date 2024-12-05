package com.group11.service.impl;

import com.group11.entity.UserEntity;
import com.group11.repository.UserRepository;
import com.group11.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }

        // Convert UserEntity to UserDetails
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoleName()) // Assuming 'role' is a string, you may need to modify this part if role is more complex
                .build();
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }
}
