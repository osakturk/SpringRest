package com.etpa.smd.task.client

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.example.springboot.constants.ObjectConstants.S3_BUCKET_NAME
import com.example.springboot.providers.AmazonS3Provider
import com.example.springboot.utils.FileLocationUtil
import io.findify.s3mock.S3Mock
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class AmazonS3ClientTest {

    private val port: Int = 9001

    private fun getAmazonS3Mock(): AmazonS3 {

        val api: S3Mock = S3Mock.Builder().withPort(port).withInMemoryBackend().build()
        api.start()

        val endpoint = EndpointConfiguration("http://localhost:$port", "eu-central-1")

        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials()))
                .build();
    }

    /**
     * WHEN calling AmazonS3 client to download the file
     * THEN the image file should be downloaded from the bucket
     **/
    @Test
    fun `test downloading image file from s3`() {
        val fileLocationUtil = FileLocationUtil()
        val amazonS3client = AmazonS3Provider(getAmazonS3Mock())
        amazonS3client.amazonS3.createBucket(S3_BUCKET_NAME)
        val predefineTypeName = "thumnail"
        val reference = "ASDASDASD.jpg"

        val filename = amazonS3client.downloadFile(predefineTypeName, reference)
        assertNotNull(filename)

        val expectedFilename = fileLocationUtil.getFileLocation(reference, predefineTypeName)

        val objectListing = amazonS3client.amazonS3.listObjects(S3_BUCKET_NAME)
        assertEquals(objectListing.objectSummaries.size, 1)
        assertEquals(objectListing.objectSummaries[0].bucketName,S3_BUCKET_NAME)
        assertEquals(objectListing.objectSummaries[0].key, expectedFilename)
    }

    /**
     * WHEN calling AmazonS3 client to flush the file
     * THEN the image file should be deleted from the bucket
     **/
    @Test
    fun `test flush image file from s3`() {
        val amazonS3client = AmazonS3Provider(getAmazonS3Mock())
        amazonS3client.amazonS3.createBucket(S3_BUCKET_NAME)
        val predefineTypeName = "thumnail"
        val reference = "ASDASDASD.jpg"

        val filename = amazonS3client.flushFile(predefineTypeName, reference)
        assertNotNull(filename)

        val objectListing = amazonS3client.amazonS3.listObjects(S3_BUCKET_NAME)
        assertEquals(objectListing.objectSummaries.size, 0)
    }
}