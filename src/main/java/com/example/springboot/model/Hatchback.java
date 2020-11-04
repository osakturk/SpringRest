package com.example.springboot.model;

import com.example.springboot.service.ICar;

public class Hatchback implements ICar {

    @Override
    public String getType() {
        return "Hatchback Car has produced. ";
    }

    @Override
    public String toString() {
        return getType();
    }
}
