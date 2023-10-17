package com.gauge.common.utils;

import software.amazon.awssdk.awscore.exception.AwsServiceException;

public class AWSExceptions extends AwsServiceException {
    public AWSExceptions(Builder b) {
        super(b);
    }
}
