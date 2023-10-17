package com.gauge.common.steps;

import com.gauge.aws.client.EC2TestClient;
import com.gauge.aws.client.S3TestClient;
import com.gauge.common.constants.ImageId;
import com.gauge.common.constants.TestConstants;
import com.gauge.common.utils.ScenarioContext;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.TestContext;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@RequiredArgsConstructor
public class AWSteps {

    private final S3TestClient s3TestClient;
    private final EC2TestClient ec2TestClient;

    @Step("Create new S3 bucket with file and content")
    public void createBucket() throws InterruptedException {
        String bucketFullName = TestConstants.BUCKET_NAME + System.currentTimeMillis();
        String fileName = TestConstants.BUCKET_FILE;

        s3TestClient.createS3Bucket(bucketFullName);
        s3TestClient.uploadS3FileAndContent(bucketFullName, fileName);

        ScenarioContext.setData(TestConstants.BUCKET_NAME, bucketFullName);
        ScenarioContext.setData(fileName, fileName);
    }

    @Step("Cleanup S3 content")
    public void removeS3Objects() {
        String bucketFullName = (String) ScenarioContext.getData(TestConstants.BUCKET_NAME);
        String fileName = (String) ScenarioContext.getData(TestConstants.BUCKET_FILE);

        try {
            s3TestClient.deleteBucketAndData(bucketFullName, fileName);
        } catch (AwsServiceException ex) {
            log.error("Some objects could not be removed.Check AWS infrastructure!");
            log.error(ex.awsErrorDetails().toString());
        }
    }

    @Step("Create EC2 <instanceType> instance with <imageId>")
    public void createEc2Instance(String instanceType, String imageId) {
        String instanceId = ec2TestClient.createEc2Instance(instanceType, imageId);
        ScenarioContext.setData(TestConstants.EC2_INSTANCEID, instanceId);
    }

    @Step("Set EC2 <tagKey> tag with value <tagValue>")
    public void changeEc2Tags(String tagKey, String tagValue){
        String instanceId = (String) ScenarioContext.getData(TestConstants.EC2_INSTANCEID);
        ec2TestClient.changeInstanceTags(instanceId, tagKey, tagValue);
    }
}