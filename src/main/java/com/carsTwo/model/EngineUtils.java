package com.carsTwo.model;

import com.carsTwo.model.enums.EngineType;

import java.util.function.Function;

public interface EngineUtils {



    Function<Engine, Double> toPower = engine -> engine.power;
    Function<Engine, EngineType> toEngineType = engine -> engine.type;
}
