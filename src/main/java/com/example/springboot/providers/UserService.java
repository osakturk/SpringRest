package com.example.springboot.providers;

import com.example.springboot.converter.UserConverter;
import com.example.springboot.dao.UserDao;
import com.example.springboot.dto.UserDto;
import com.example.springboot.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record UserService(UserDao userDao) {

    public User createUser(UserDto user) {
        return userDao.save(UserConverter.convertDtoToDbModel(user));
    }

    public User updateUser(UserDto user, Long id) {
        User dbModel = UserConverter.convertDtoToDbModel(user);
        dbModel.setId(id);
        return userDao.save(dbModel);
    }

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUser(Long id) {
        return userDao.findById(id).get();
    }
}
