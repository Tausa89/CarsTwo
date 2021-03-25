package com.carsTwo.model;

import java.math.BigDecimal;
import java.util.function.Function;

public interface CarUtils {






    Function<Car, String> toModel = car -> car.model;
    Function<Car, BigDecimal> toPrice = car -> car.price;
    Function<Car, Double> toMileage = car -> car.mileage;
    Function<Car, Engine> toEngine = car -> car.engine;
    Function<Car, CarBody> toCarBody = car -> car.carBody;
    Function<Car, Wheel> toWheel = car -> car.wheel;
}
