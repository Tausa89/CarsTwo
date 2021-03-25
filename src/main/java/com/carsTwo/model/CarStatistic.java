package com.carsTwo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarStatistic {
    private Statistic<BigDecimal> price;
    private Statistic<Double> mileage;
    private Statistic<Double> power;


}
