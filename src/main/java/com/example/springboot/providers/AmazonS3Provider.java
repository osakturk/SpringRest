package com.example.springboot.providers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.springboot.utils.FileLocationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.springboot.constants.ObjectConstants.S3_BUCKET_NAME;

@Service
public record AmazonS3Provider(AmazonS3 amazonS3) {

    private static final Logger log = LoggerFactory.getLogger(AmazonS3Provider.class);

    public byte[] downloadFile(String predefinedTypeName, String reference) {
        FileLocationUtil locationUtil = new FileLocationUtil();
        try {
            byte[] content;
            final S3Object s3Object = amazonS3.getObject(S3_BUCKET_NAME, locationUtil.getFileLocation(reference, predefinedTypeName));
            final S3ObjectInputStream stream = s3Object.getObjectContent();
            content = IOUtils.toByteArray(stream);
            log.info("Received content from S3. The content is : {}", content);
            s3Object.close();
            return content;
        } catch (IOException | AmazonClientException ioException) {
            log.info("Error when downloading image");
            ioException.printStackTrace();
        }

        return new byte[0];
    }


    public void flushFile(String predefinedTypeName, String reference) {
        FileLocationUtil locationUtil = new FileLocationUtil();
        try {
            amazonS3.deleteObject(S3_BUCKET_NAME, locationUtil.getFileLocation(reference, predefinedTypeName));
        } catch (AmazonServiceException e) {
            log.error("Error when flushing the object");
            e.printStackTrace();
        }
    }


}
