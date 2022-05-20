package com.example.springboot.utils;

import com.example.springboot.constants.ObjectConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class FileUploadUtil {

    private FileUploadUtil() {}

    public static Map<String, String> saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Map<String, String> fileResponse = new HashMap<>();

        Path uploadPath = Paths.get("Files-Upload");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        fileResponse.put(ObjectConstants.FILE_CODE, fileCode);
        fileResponse.put(ObjectConstants.FILE_NAME, multipartFile.getOriginalFilename());
        fileResponse.put(ObjectConstants.FILE_SIZE, String.valueOf(multipartFile.getSize()));
        fileResponse.put(ObjectConstants.FILE_PATH, "/downloadFile/" + fileCode);
        fileResponse.put(ObjectConstants.FILE_EXTENSION, multipartFile.getContentType());
        return fileResponse;
    }
}
