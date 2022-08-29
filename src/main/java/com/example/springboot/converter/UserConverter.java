package com.example.springboot.converter;

import com.example.springboot.dto.UserDto;
import com.example.springboot.model.User;

public class UserConverter {

    public static User convertDtoToDbModel(UserDto userDto){
        User userDbModel = new User();
        userDbModel.setEmail(userDto.getEmail());
        userDbModel.setFullName(userDto.getFullName());
        userDbModel.setGender(userDto.getGender());
        userDbModel.setPassword(userDto.getPassword());
        return  userDbModel;
    }
}
