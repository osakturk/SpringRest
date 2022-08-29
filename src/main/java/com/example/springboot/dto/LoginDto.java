package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    @NotBlank
    @Email(message = "email was wrong. please check your email")
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 6, max = 10, message = "password should have at least 6 characters and at most 10 characters")
    private String password;
}
