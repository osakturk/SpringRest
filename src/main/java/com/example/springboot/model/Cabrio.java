package com.example.springboot.model;

import com.example.springboot.service.ICar;

public class Cabrio implements ICar {

    @Override
    public String getType() {
        return "Cabrio Car has produced. ";
    }

    @Override
    public String toString() {
        return getType();
    }
}
