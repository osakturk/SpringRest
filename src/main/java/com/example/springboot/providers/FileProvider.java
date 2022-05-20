package com.example.springboot.providers;

import com.example.springboot.constants.ObjectConstants;
import com.example.springboot.dao.FileDao;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.MissingParameterException;
import com.example.springboot.model.File;
import com.example.springboot.utils.FileDownloadUtil;
import com.example.springboot.utils.FileExtensionType;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Service
public class FileProvider {

    private final FileDao fileDao;

    public FileProvider(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public ResponseEntity<Object> getObjectResponseEntity(Map<String, String> fileParameters, HttpStatus status, Long id) {
        JSONObject response = new JSONObject();
        if (!fileParameters.containsKey(ObjectConstants.FILE_PATH) || fileParameters.get(ObjectConstants.FILE_PATH).isEmpty()) {
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("filePath is required"));
        }
        if (!fileParameters.containsKey(ObjectConstants.FILE_SIZE) || fileParameters.get(ObjectConstants.FILE_SIZE).isEmpty()) {
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("fileSize is required"));
        }
        if (!fileParameters.containsKey(ObjectConstants.FILE_NAME) || fileParameters.get(ObjectConstants.FILE_NAME).isEmpty()) {
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("fileName is required"));
        }
        if (!fileParameters.containsKey(ObjectConstants.FILE_EXTENSION) || fileParameters.get(ObjectConstants.FILE_EXTENSION).isEmpty()) {
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("extension is required"));
        }
        if (!FileExtensionType.isTypeTrue(fileParameters.get(ObjectConstants.FILE_EXTENSION))){
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("wrong extension type" ));
        }
        if (Long.parseLong(fileParameters.get(ObjectConstants.FILE_SIZE)) > 5242880){
            return new CustomExceptionHandler().handleRequiredField(new MissingParameterException("File size can not be larger than 5MB"));
        }
        File file;
        if (id != null && id > 0) {
            file = fileDao.findOne(id);
            file.setFileName(fileParameters.get(ObjectConstants.FILE_NAME));
            file.setFileSize(fileParameters.get(ObjectConstants.FILE_SIZE));
            file.setFilePath(fileParameters.get(ObjectConstants.FILE_PATH));
            file.setFileExtension(fileParameters.get(ObjectConstants.FILE_EXTENSION));
            file.setUpdateDate(new Date());
        } else {
            file = new File(fileParameters.get(ObjectConstants.FILE_PATH),
                    fileParameters.get(ObjectConstants.FILE_SIZE),
                    fileParameters.get(ObjectConstants.FILE_NAME),
                    fileParameters.get(ObjectConstants.FILE_EXTENSION));
        }
        fileDao.save(file);
        response.put("id", file.getId());
        response.put(ObjectConstants.FILE_NAME, file.getFileName());
        return new ResponseEntity<>(response.toString(), status);
    }

    public ResponseEntity<Object> getDownloadedFile(String fileCode){
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return new CustomExceptionHandler().handleUnexpectedField(new Exception("Server Error"));
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    public Iterable<File> findAll() {
        return fileDao.findAll();
    }

    public ResponseEntity<Object> destroy(Long id) {
        fileDao.delete(id);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
