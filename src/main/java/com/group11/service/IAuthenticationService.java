package com.group11.service;

import com.group11.entity.UserEntity;
import com.group11.model.LoginUserModel;
import com.group11.model.RegisterUserModel;

public interface IAuthenticationService {
    UserEntity signup(RegisterUserModel input);

    UserEntity authenticate(LoginUserModel input);

    void ChangePassword(String email, String password);
}
