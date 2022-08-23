package com.example.springboot.controllers;

import com.example.springboot.providers.AmazonS3Provider;
import com.example.springboot.utils.FileUploadUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final AmazonS3Provider serviceProvider;

    public ImageController(AmazonS3Provider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @PostMapping("/show/predefinedTypeName/dummySeoName/")
    public ResponseEntity<Object> store(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Map<String, String> fileInfo = FileUploadUtil.saveFile(fileName, multipartFile);
//        return serviceProvider.uploadFile(fileInfo, HttpStatus.CREATED, null);
        return null;
    }

    @GetMapping("/show/predefinedTypeName/dummySeoName/")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String predefinedTypeName, @PathVariable(required = false) String dummySeoName, @RequestParam String reference) {
        byte[] data = serviceProvider.downloadFile(predefinedTypeName, reference);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type","application/octet-stream")
                .header("content-disposition","attachment; filename=\"" + reference + "\"")
                .header("Cache-Control", "no-cache")
                .body(resource);

    }


    @PutMapping("/update/predefinedTypeName/dummySeoName/")
    public ResponseEntity<Object> updateFile(@PathVariable String predefinedTypeName, @PathVariable(required = false) String dummySeoName, @RequestParam String reference) {
        serviceProvider.flushFile(predefinedTypeName, reference);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/flush/predefinedTypeName/dummySeoName/")
    public ResponseEntity<Object> flushFile(@PathVariable String predefinedTypeName, @PathVariable(required = false) String dummySeoName, @RequestParam String reference) {
        serviceProvider.flushFile(predefinedTypeName, reference);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
