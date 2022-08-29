package com.example.springboot.utils;

import java.util.Base64;

public class PasswordUtil {

    PasswordUtil() {
    }

    public static String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
