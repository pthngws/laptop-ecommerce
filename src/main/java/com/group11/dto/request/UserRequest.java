package com.group11.dto.request;

import com.group11.entity.AddressEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

public class UserRequest {
    private Long userID;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String phone;

    private String roleName;

    private Boolean active = true;

    private AddressEntity address;


}
