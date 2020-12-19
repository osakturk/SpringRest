package com.example.springboot.controllers;

import com.example.springboot.dao.TeamDao;
import com.example.springboot.model.Team;
import com.example.springboot.providers.TeamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    @Autowired
    TeamDao teamDao;

    @Autowired
    TeamProvider teamProvider;

    @GetMapping("/teams")
    public Iterable<Team> index() {

        return teamDao.findAll();
    }

    @GetMapping("/teams/{id}")
    public Team show(@PathVariable("id") long id) {

        return teamDao.findOne(id);
    }

    @PostMapping(path = "/teams", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> store(@RequestBody String requestBody) {

        return teamProvider.getObjectResponseEntity(requestBody, HttpStatus.ACCEPTED, null);
    }

    @PutMapping(path = "/teams/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody String requestBody, @PathVariable("id") long id) {

        return teamProvider.getObjectResponseEntity(requestBody, HttpStatus.ACCEPTED, id);
    }

    @DeleteMapping("/teams/{id}")
    public void destroy(@PathVariable("id") long id) {
        teamDao.delete(id);
    }
}
