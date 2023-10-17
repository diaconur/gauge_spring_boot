package com.gauge.common.configuration;

import com.gauge.aws.configuration.AWSConfiguration;
import com.gauge.common.utils.ScenarioContext;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@Import(AWSConfiguration.class)
public class BaseConfiguration {

//    @Bean
//    ScenarioContext scenarioContext(){
//        return new ScenarioContext();
//    }
}
