package com.example.springboot.dao;

import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

}
