package com.example.springboot.dao;

import com.example.springboot.model.League;
import org.springframework.data.repository.CrudRepository;

public interface LeagueDao extends CrudRepository<League, Long> {
}
