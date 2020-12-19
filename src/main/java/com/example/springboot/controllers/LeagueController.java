package com.example.springboot.controllers;

import com.example.springboot.dao.CountryDao;
import com.example.springboot.dao.LeagueDao;
import com.example.springboot.model.League;
import com.example.springboot.providers.LeagueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeagueController {

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    CountryDao countryDao;

    @Autowired
    LeagueProvider leagueProvider;
    

    @GetMapping("/leagues")
    public Iterable<League> index() {

        return leagueDao.findAll();
    }

    @GetMapping("/leagues/{id}")
    public League show(@PathVariable("id") long id) {

        return leagueDao.findOne(id);
    }

    @PostMapping(path = "/leagues", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> store(@RequestBody String requestBody) {
        return leagueProvider.getObjectResponseEntity(requestBody, HttpStatus.CREATED, null);
    }

    @PutMapping(path = "/leagues/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody String requestBody, @PathVariable("id") long id) {
        return leagueProvider.getObjectResponseEntity(requestBody, HttpStatus.ACCEPTED, id);
    }

    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") long id) {
        League league = leagueDao.findOne(id);
        leagueDao.delete(league);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
