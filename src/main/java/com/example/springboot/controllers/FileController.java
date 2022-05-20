package com.example.springboot.controllers;

import com.example.springboot.model.File;
import com.example.springboot.providers.FileProvider;
import com.example.springboot.utils.FileUploadUtil;
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
@RequestMapping("/api/files")
public class FileController {

    private final FileProvider fileProvider;

    public FileController(FileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }

    @GetMapping("/")
    public Iterable<File> index() {
        return fileProvider.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Object> store(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Map<String, String> fileInfo = FileUploadUtil.saveFile(fileName, multipartFile);
        return fileProvider.getObjectResponseEntity(fileInfo, HttpStatus.CREATED, null);
    }

    @GetMapping("/{fileCode}")
    public ResponseEntity<Object> downloadFile(@PathVariable("fileCode") String fileCode) {
        return fileProvider.getDownloadedFile(fileCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") long id) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Map<String, String> fileInfo = FileUploadUtil.saveFile(fileName, multipartFile);
        return fileProvider.getObjectResponseEntity(fileInfo, HttpStatus.ACCEPTED, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") long id) {
        return fileProvider.destroy(id);
    }
}
