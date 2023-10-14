package com.gauge.bonus.data;

import lombok.Getter;

@Getter
public enum BonusTypes {

    MEGA_BONUS("Mega Bonus",123),
    SUPER_BONUS("Super Bonus",312);

    private String bonusType;
    private Integer bonusId;

    BonusTypes(String bonusType, Integer bonusId){
        this.bonusId= bonusId;
        this.bonusType=bonusType;
    }
}
