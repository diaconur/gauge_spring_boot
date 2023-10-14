package com.gauge.bonus.steps;

import com.gauge.bonus.client.BonusTestClient;
import com.gauge.bonus.data.Bonus;
import com.gauge.bonus.data.BonusRequest;
import com.gauge.bonus.data.BonusTypes;
import com.thoughtworks.gauge.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Assert;

import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
public class BonusSteps {

    private final BonusTestClient bonusTestClient;

    @Step("Retrieve all bonuses")
    public void getAllBonuses(){

        Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.TWO_SECONDS).untilAsserted(()->{
            bonusTestClient.getAllBonuses();
            assert true;
        });
    }

    @Step("Retrieve bonus id <id>")
    public void getAllBonuses(Integer id){

        Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.TWO_SECONDS).untilAsserted(()->{
           Bonus bonus = bonusTestClient.getBonusById(id);
           log.info("Bonus contains id "+bonus.getId());
            Assert.assertEquals(id,"3");
        });
    }

    @Step("Create new <bonusType> bonus")
    public void createNewBonus(BonusTypes bonusTypes){
        BonusRequest bonusRequest = BonusRequest.builder()
                .bonusName("New Mega Bonus "+bonusTypes.getBonusId())
                .bonusType(bonusTypes.getBonusType())
                .startTime(Date.valueOf(LocalDate.now()))
                .endTime(Date.valueOf(LocalDate.now().plusDays(2))).build();

        bonusTestClient.addBonus(bonusRequest);
    }

    @Step("Delete bonus <id>")
    public void deleteBonusById(Integer id){
        bonusTestClient.deleteBonusById(id);
    }
}
