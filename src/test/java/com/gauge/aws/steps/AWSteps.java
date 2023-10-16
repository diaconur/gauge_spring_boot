package com.gauge.aws.steps;

import com.gauge.aws.client.S3TestClient;
import com.thoughtworks.gauge.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AWSteps {

    private final S3TestClient s3TestClient;

    @Step("Create S3 bucket")
    public void createBucket(){
        s3TestClient.createNewBucket();
    }
}
