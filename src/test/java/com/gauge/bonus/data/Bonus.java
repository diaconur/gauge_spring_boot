package com.gauge.bonus.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bonus {

    Integer id;
    String bonusName;
    String bonusType;
    Date startTime;
    Date endTime;
}
