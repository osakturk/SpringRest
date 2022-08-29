package com.example.springboot.providers;

import com.example.springboot.dao.UserDao;
import com.example.springboot.dao.UserTokenDao;
import com.example.springboot.dto.LoginDto;
import com.example.springboot.model.User;
import com.example.springboot.model.UserToken;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static com.example.springboot.utils.PasswordUtil.encodePassword;

@Service
public record AuthorizationService(UserDao userDao, UserTokenDao userTokenDao) {

    public ResponseEntity<Object> login(LoginDto loginDto) {
        User user = userDao.findByEmailAndPassword(loginDto.getEmail(), encodePassword(loginDto.getPassword()));
        if (user == null) {
            JSONObject response = new JSONObject();
            response.put("result", "Email or password is wrong");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        UserToken newToken = createOrUpdateUserToken(user);
        JSONObject response = new JSONObject();
        response.put("token", newToken.getToken());
        response.put("email", newToken.getUser().getEmail());

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    public UserToken createOrUpdateUserToken(User user) {
        UserToken previousToken = userTokenDao.findByUserIdAndIsValid(user.getId(), true);
        UserToken newToken = new UserToken(UUID.randomUUID().toString(), true, Date.from(Instant.now().plus(10, ChronoUnit.DAYS)), user);
        if (previousToken != null) {
            previousToken.setIsValid(false);
            userTokenDao.save(previousToken);
        }
        return userTokenDao.save(newToken);
    }
}
