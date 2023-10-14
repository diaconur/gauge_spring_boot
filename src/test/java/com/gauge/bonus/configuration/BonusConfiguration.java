package com.gauge.bonus.configuration;

import com.gauge.bonus.client.BonusClient;
import com.gauge.bonus.client.BonusTestClient;
import com.gauge.bonus.steps.BonusSteps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusConfiguration {

    @Bean
    public BonusTestClient bonusTestClient(BonusClient bonusClient){
        return new BonusTestClient(bonusClient);
    }

    @Bean
    public BonusSteps bonusSteps(BonusTestClient bonusTestClient){
        return new BonusSteps(bonusTestClient);
    }
}
