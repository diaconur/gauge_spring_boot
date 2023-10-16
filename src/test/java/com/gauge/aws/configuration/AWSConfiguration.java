package com.gauge.aws.configuration;

import com.gauge.aws.client.S3TestClient;
import com.gauge.aws.steps.AWSteps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.identity.spi.AwsCredentialsIdentity;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class AWSConfiguration {
    String accessKeyId = "";
    String secretKeyId = "";

    @Bean
    public S3TestClient s3TestClient(S3Client s3Client){
        return new S3TestClient(s3Client);
    }

    @Bean
    public AWSteps awSteps(S3TestClient s3TestClient){
        return new AWSteps(s3TestClient);
    }

    public AwsCredentialsProvider credentials(){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretKeyId);
        AwsCredentialsProvider provider = () -> awsBasicCredentials;
        return provider;
    }

    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .credentialsProvider(credentials())
                .region(Region.EU_CENTRAL_1)
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();

    }
}
