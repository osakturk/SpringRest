package com.example.springboot.controllers;

import com.example.springboot.dao.PlayerDao;
import com.example.springboot.model.Player;
import com.example.springboot.providers.PlayerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    @Autowired
    PlayerDao playerDao;

    @Autowired
    PlayerProvider playerProvider;

    @GetMapping("/players")
    public Iterable<Player> index() {

        return playerDao.findAll();
    }

    @GetMapping("/players/{id}")
    public Player show(@PathVariable("id") long id) {

        return playerDao.findOne(id);
    }

    @PostMapping(path = "/players", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> store(@RequestBody String requestBody) {
        return playerProvider.getObjectResponseEntity(requestBody, HttpStatus.CREATED, null);
    }

    @PutMapping(path = "/players/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody String requestBody, @PathVariable("id") long id) {
        return playerProvider.getObjectResponseEntity(requestBody, HttpStatus.CREATED, id);
    }

    @DeleteMapping("/players/{id}")
    public void destroy(@PathVariable("id") long id) {
        playerDao.delete(id);
    }


}
