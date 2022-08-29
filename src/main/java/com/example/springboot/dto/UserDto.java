package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "name and surname can not be null")
    private String fullName;
    @NotBlank(message = "gender can not be null")
    private String gender;
    @Email(message = "email was wrong. please check your email")
    private String email;
    @Size(min = 6, max = 10, message = "password should have at least 6 characters and at most 10 characters")
    @NotBlank(message = "password can not be null")
    private String password;
}
