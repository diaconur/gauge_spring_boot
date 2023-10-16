package com.gauge.common.configuration;

import com.gauge.aws.configuration.AWSConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@Import(AWSConfiguration.class)
public class BaseConfiguration {

}
