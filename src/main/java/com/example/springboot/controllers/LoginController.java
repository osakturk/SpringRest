package com.example.springboot.controllers;

import com.example.springboot.dto.LoginDto;
import com.example.springboot.providers.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final AuthorizationService authorizationService;

    LoginController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto){
        return authorizationService.login(loginDto);
    }
}
