package com.gauge.common.configuration;

import com.thoughtworks.gauge.BeforeSuite;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.StandardEnvironment;

import static com.thoughtworks.gauge.ClassInstanceManager.setClassInitializer;

public class RegisterIOC {

    private ConfigurableApplicationContext context;

    @BeforeSuite
    public void registerIoc() {
        String environment = new StandardEnvironment().getProperty("environment.name", "test");
        context = new SpringApplicationBuilder(BaseConfiguration.class)
                .web(WebApplicationType.NONE)
                .profiles(environment)
                .run();

        setClassInitializer(classToInitialize -> context.getBean(classToInitialize));
    }

}