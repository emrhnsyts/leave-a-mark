package com.emrhnsyts.leaveamark.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class AppUserRequest {
    @Length(min = 5,max = 15,message = "Username length must be between 5 and 15.")
    @NotBlank(message = "Username field can not be null.")
    private String username;

    @Length(min = 5,max = 15,message = "Password length must be between 5 and 15.")
    @NotBlank(message = "Password field can not be null.")
    private String password;
    @Email(message = "Email should be valid.")
    private String email;
}
