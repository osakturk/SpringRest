package com.example.springboot.enumerations;

import com.example.springboot.model.Cabrio;
import com.example.springboot.model.Hatchback;
import com.example.springboot.model.Sedan;

public enum CarTypeEnum {
    SEDAN("sedan", new Sedan().getType()),
    HATCHBACK("hatchback", new Hatchback().getType()),
    CABRIO("cabrio", new Cabrio().getType());

    private String type;
    private String response;

    CarTypeEnum(String type, String response) {
        this.type = type;
        this.response = response;
    }

    public static CarTypeEnum fromValue(Integer value) {
        CarTypeEnum[] carTypeEnums = values();
        for (CarTypeEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.getResponse().equals(value))
                return carTypeEnum;
        }

        return null;
    }

    public static CarTypeEnum fromKey(String key) {
        CarTypeEnum[] carTypeEnums = values();
        for (CarTypeEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.getType().equalsIgnoreCase(key))
                return carTypeEnum;
        }

        return null;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
