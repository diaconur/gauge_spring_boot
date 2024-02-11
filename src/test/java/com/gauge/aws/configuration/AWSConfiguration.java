package com.gauge.aws.configuration;

import com.gauge.aws.client.EC2TestClient;
import com.gauge.aws.client.S3TestClient;
import com.gauge.common.steps.AWSteps;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3Client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class AWSConfiguration {

    @Bean
    AWSConfigProperties awsConfigProperties() {
        return new AWSConfigProperties();
    }

    @Bean
    public S3TestClient s3TestClient(S3Client s3Client) {
        return new S3TestClient(s3Client);

    }

    @Bean
    public EC2TestClient ec2TestClient(Ec2Client ec2Client){
        return new EC2TestClient(ec2Client);
    }
    @Bean
    public AWSteps awSteps(S3TestClient s3TestClient, EC2TestClient ec2TestClient) {
        return new AWSteps(s3TestClient, ec2TestClient);
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .forcePathStyle(true)
                .credentialsProvider(credentials())
                .region(Region.of(awsConfigProperties().getRegion()))
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();

    }

    @Bean
    public Ec2Client ec2Client(){
        return Ec2Client.builder()
                .credentialsProvider(credentials())
                .region(Region.of(awsConfigProperties().getRegion()))
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();
    }
    public AwsCredentialsProvider credentials() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials
                .create(awsConfigProperties().getAccessKey(),
                        awsConfigProperties().getSecretKey());
        AwsCredentialsProvider provider = () -> awsBasicCredentials;
        return provider;
    }
}
