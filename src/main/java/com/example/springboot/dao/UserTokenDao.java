package com.example.springboot.dao;

import com.example.springboot.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenDao extends JpaRepository<UserToken, Long> {
    UserToken findByUserIdAndIsValid(Long userId, Boolean isValid);
}
