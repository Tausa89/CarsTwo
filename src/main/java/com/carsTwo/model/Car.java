package com.carsTwo.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {


    String model;
    BigDecimal price;
    double mileage;
    Engine engine;
    CarBody carBody;
    Wheel wheel;



}
