package com.example.springboot.dao;

import com.example.springboot.model.Country;
import org.springframework.data.repository.CrudRepository;


public interface CountryDao extends CrudRepository<Country, Long> {

}
