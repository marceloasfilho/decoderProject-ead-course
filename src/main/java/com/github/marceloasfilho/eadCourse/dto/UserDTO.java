package com.github.marceloasfilho.eadCourse.dto;

import com.github.marceloasfilho.eadCourse.enums.UserStatus;
import com.github.marceloasfilho.eadCourse.enums.UserType;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String oldPassword;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private UserStatus userStatus;
    private UserType userType;
}
