package com.example.springboot.model;

import com.example.springboot.service.ICar;

public class Sedan implements ICar {

    @Override
    public String getType() {
        return "Sedan Car has produced. ";
    }

    @Override
    public String toString() {
        return getType();
    }
}
