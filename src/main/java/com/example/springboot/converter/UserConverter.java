package com.example.springboot.converter;

import com.example.springboot.dto.UserDto;
import com.example.springboot.model.User;

import static com.example.springboot.utils.PasswordUtil.encodePassword;

public class UserConverter {

    UserConverter(){}

    public static User convertDtoToDbModel(UserDto userDto){
        User userDbModel = new User();
        userDbModel.setEmail(userDto.getEmail());
        userDbModel.setFullName(userDto.getFullName());
        userDbModel.setGender(userDto.getGender());
        userDbModel.setPassword(encodePassword(userDto.getPassword()));
        return  userDbModel;
    }
}
