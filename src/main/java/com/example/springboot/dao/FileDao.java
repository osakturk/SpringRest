package com.example.springboot.dao;

import com.example.springboot.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileDao extends CrudRepository<File, Long> {
}
