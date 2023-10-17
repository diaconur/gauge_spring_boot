package com.gauge.aws.client;

import com.gauge.common.constants.ImageId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

@Slf4j
@RequiredArgsConstructor
public class EC2TestClient {

    private final Ec2Client ec2Client;

    public String createEc2Instance(String instanceType, String imageId){
        ImageId imageIdEc2 = ImageId.valueOf(imageId);
        RunInstancesRequest request = RunInstancesRequest
                .builder()
                .imageId(imageIdEc2.getAmiId())
                .additionalInfo(imageIdEc2.getImageType())
                .instanceType(InstanceType.valueOf(instanceType))
                .maxCount(1)
                .minCount(1)
                .build();

        RunInstancesResponse response = ec2Client.runInstances(request);
        String instanceId = response.instances().get(0).instanceId();
        log.info("Created EC2 instance with ID {} ", instanceId);
        return instanceId;
    }

    public void changeInstanceTags(String instanceId ,String key, String value){
        Tag ec2Tag = Tag.builder()
                .key(key)
                .value(value)
                .build();
        CreateTagsRequest tagsRequest = CreateTagsRequest
                .builder()
                .resources(instanceId)
                .tags(ec2Tag)
                .build();
        ec2Client.createTags(tagsRequest);
        ec2Client.waiter().waitUntilInstanceRunning(DescribeInstancesRequest.builder().instanceIds(instanceId).build());
        log.info("EC2 instance was successfully started!");
    }
}
