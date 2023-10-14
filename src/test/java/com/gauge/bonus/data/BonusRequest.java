package com.gauge.bonus.data;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class BonusRequest {

    String bonusName;
    String bonusType;
    Date startTime;
    Date endTime;
}
