package com.gauge.bonus.client;


import com.gauge.bonus.data.Bonus;
import com.gauge.bonus.data.BonusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "bonus-service")
public interface BonusClient {

    @GetMapping("/bonus/getAllBonuses")
    public String getAllBonuses();

    @PostMapping("/bonus/addBonus")
    public void createBonus(@RequestBody BonusRequest bonusRequest);

    @DeleteMapping("/bonus/deleteBonus/{id}")
    public void deleteBonusById(@RequestParam("id2") Integer id2);

    @GetMapping("/bonus/getBonusById")
    public Bonus getBonusById(@RequestParam("id") Integer id);
}
