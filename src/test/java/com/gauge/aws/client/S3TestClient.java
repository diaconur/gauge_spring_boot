package com.gauge.aws.client;


import com.gauge.common.constants.TestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Slf4j
@RequiredArgsConstructor
public class S3TestClient {

    private final S3Client s3Client;

    public void uploadS3FileAndContent(String bucket, String fileName) {
        log.info("Starting file upload {} with content {}", fileName, TestConstants.BUCKET_FILE_CONTENT);
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build(),
                RequestBody.fromString(TestConstants.BUCKET_FILE_CONTENT));
        log.info("File was successfully uploaded!");
    }

    public void createS3Bucket(String bucketName) {
        try {
            s3Client.createBucket(CreateBucketRequest
                    .builder()
                    .bucket(bucketName)
                    .build());
            log.info("Creating S3 bucket {}", bucketName);
            s3Client.waiter().waitUntilBucketExists(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
            log.info(bucketName + " was created successfully!");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public void deleteBucketAndData(String bucketName, String fileName) {
        try {
            log.info("Trying to delete bucket data");
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(fileName).build();
            s3Client.deleteObject(deleteObjectRequest);

            log.info("Trying to delete bucket");
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
            s3Client.deleteBucket(deleteBucketRequest);
            log.info("Bucket {} with data {} were deleted!", bucketName, fileName);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
