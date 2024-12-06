package com.group11.service;

import com.group11.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {

    UserEntity findByEmail(String email);
    void save(UserEntity user);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<UserEntity> allUsers();
    // Phương thức yêu cầu trả về UserDetails
    UserEntity findById(Long id);
    Page<UserEntity> getAllUsers(int page, int size);
    Page<UserEntity> getAllUsersByRoleName(String roleName, int page, int size);
    boolean activeUser(Long id, boolean active);

    Page<UserEntity> searchUser(String keyword, int page, int size);

}
