package com.example.springboot.utils;

public enum FileExtensionType {
    JPG("image/jpg"),
    PNG("image/png"),
    JPEG("image/jpeg"),
    DOCX("docx"),
    PDF("pdf"),
    XLSX("xlsx");

    final String extension;

    FileExtensionType(String extension) {
        this.extension = extension;
    }

    public static boolean isTypeTrue(String type){
        FileExtensionType[] extensionTypes = values();

        for (FileExtensionType ex: extensionTypes) {
            if (ex.extension.equals(type)){
                return true;
            }
        }
        return false;
    }
}
