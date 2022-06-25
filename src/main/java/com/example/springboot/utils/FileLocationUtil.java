package com.example.springboot.utils;

public class FileLocationUtil {
    public String getFileLocation(String reference, String predefinedTypeName) {
        String fileNameWithoutLocation = reference.substring(0, reference.lastIndexOf('.'));
        if (fileNameWithoutLocation.length() <= 4){
            return predefinedTypeName + "/" + reference;
        }
        else if (fileNameWithoutLocation.length() < 8) {
            return predefinedTypeName + "/" + reference.substring(0, 4) + "/" + reference;
        }
        else {
            return predefinedTypeName + "/" + reference.substring(0, 4) + "/" + reference.substring(5,8) + "/" + reference;
        }
    }
}
