package com.example.springboot.controllers;


import com.example.springboot.dto.UserDto;
import com.example.springboot.providers.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto user){
        return ResponseEntity.created(URI.create("/user")).body(userService.createUser(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto user, @PathVariable Long id){
        return ResponseEntity.accepted().body(userService.updateUser(user, id));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
}
