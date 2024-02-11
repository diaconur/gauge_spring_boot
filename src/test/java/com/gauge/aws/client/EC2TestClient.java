package com.gauge.aws.client;

import java.util.List;
import java.security.InvalidParameterException;
import com.gauge.common.constants.ImageId;
import com.gauge.common.constants.TestConstants;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;
import software.amazon.awssdk.services.ec2.waiters.Ec2Waiter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    public void stopInstance(String instanceId) {
        StopInstancesRequest request = StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        log.info("Use an Ec2Waiter to wait for the instance to stop. This will take a few minutes.");
        ec2Client.stopInstances(request);
    }

    public WaiterResponse<DescribeInstancesResponse> waiterResponse (DescribeInstancesRequest instanceRequest, String actionToCheck){
        Ec2Waiter ec2Waiter = Ec2Waiter.builder()
                .overrideConfiguration(b -> b.maxAttempts(TestConstants.EC2_WAITER_MAX_ATTEMPTS))
                .client(ec2Client)
                .build();
        WaiterResponse<DescribeInstancesResponse> waiterResponse = null;

        switch (actionToCheck){
            case "STOPPED":
                waiterResponse = ec2Waiter.waitUntilInstanceStopped(instanceRequest);
                break;
            case "RUNNING":
                waiterResponse = ec2Waiter.waitUntilInstanceRunning(instanceRequest);
                break;
            default:
                throw new InvalidParameterException("Invalid parameter or instance state selected!");
        }

        return waiterResponse;
    }

    public void checkInstanceStatus(String status, String instanceId){
        DescribeInstancesRequest instanceRequest = DescribeInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        waiterResponse(instanceRequest, status).matched().response().ifPresent(System.out::println);
    }

    public void terminateEC2(String instanceID) {
        try {
            TerminateInstancesRequest ti = TerminateInstancesRequest.builder()
                    .instanceIds(instanceID)
                    .build();

            TerminateInstancesResponse response = ec2Client.terminateInstances(ti);
            List<InstanceStateChange> list = response.terminatingInstances();
            for (InstanceStateChange sc : list) {
                log.info("The ID of the terminated instance is " + sc.instanceId());
            }
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }


}
