package com.example.springboot.dao;

import com.example.springboot.model.TransferFee;
import org.springframework.data.repository.CrudRepository;

public interface TransferFeeDao extends CrudRepository<TransferFee, Long> {
}
