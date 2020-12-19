package com.example.springboot.controllers;

import com.example.springboot.dao.TransferFeeDao;
import com.example.springboot.model.TransferFee;
import com.example.springboot.providers.TransferFeeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferFeeController {

    @Autowired
    TransferFeeDao transferFeeDao;

    @Autowired
    TransferFeeProvider transferFeeProvider;

    @GetMapping("/fee/{id}")
    public TransferFee show(@PathVariable("id") long id) {
        TransferFee fee = transferFeeDao.findOne(id);
        fee.setFee((double) (fee.getPlayer().getMonthOfExprience() * 100000 / fee.getPlayer().getAge()));
        transferFeeDao.save(fee);
        return fee;
    }

    @PostMapping("/fee")
    public ResponseEntity<Object> store(@RequestBody String requestBody) {
        return transferFeeProvider.getObjectResponseEntity(requestBody, HttpStatus.CREATED, null);
    }

    @PutMapping("/fee/{id}")
    public ResponseEntity<Object> update(@RequestBody String requestBody, @PathVariable("id") long id) {
        return transferFeeProvider.getObjectResponseEntity(requestBody, HttpStatus.ACCEPTED, id);
    }

    @DeleteMapping("/fee/{id}")
    public void destroy(@PathVariable("id") long id){
        transferFeeDao.delete(id);
    }


}
