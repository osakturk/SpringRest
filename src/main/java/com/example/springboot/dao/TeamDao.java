package com.example.springboot.dao;

import com.example.springboot.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamDao extends CrudRepository<Team, Long> {
}
