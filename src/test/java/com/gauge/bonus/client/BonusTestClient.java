package com.gauge.bonus.client;

import com.gauge.bonus.data.Bonus;
import com.gauge.bonus.data.BonusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BonusTestClient {

    private final BonusClient bonusClient;

    public String getAllBonuses(){
        String bonuses = bonusClient.getAllBonuses();
        log.info("Bonuses are "+bonuses);
        return bonuses;
    }

    public void addBonus(BonusRequest bonusRequest){
        bonusClient.createBonus(bonusRequest);
    }

    public void deleteBonusById(Integer id){
        bonusClient.deleteBonusById(id);
    }

    public Bonus getBonusById(Integer id){
        return bonusClient.getBonusById(id);
    }
}
