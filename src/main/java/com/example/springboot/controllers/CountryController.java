package com.example.springboot.controllers;

import com.example.springboot.dao.CountryDao;
import com.example.springboot.model.Country;
import com.example.springboot.providers.CountryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountryController {

    @Autowired
    CountryDao countryDao;

    @Autowired
    CountryProvider provider;

    @GetMapping("/countries")
    public Iterable<Country> index() {
        return countryDao.findAll();
    }

    @GetMapping("/countries/{id}")
    public Country show(@PathVariable("id") long id) {
        return countryDao.findOne(id);
    }

    @PostMapping(path = "/countries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> store(@RequestBody String requestBody) {
        return provider.getObjectResponseEntity(requestBody, HttpStatus.CREATED, null);
    }

    @PutMapping(path = "/countries/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody String requestBody, @PathVariable("id") long id) {
        return provider.getObjectResponseEntity(requestBody, HttpStatus.ACCEPTED,id);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") long id) {
        countryDao.delete(id);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

}
