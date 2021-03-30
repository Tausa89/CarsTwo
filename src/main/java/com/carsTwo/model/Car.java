package com.carsTwo.model;

import com.carsTwo.model.enums.CarBodyType;
import com.carsTwo.model.enums.EngineType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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


    public boolean hasPriceGreaterThan(BigDecimal price) {
        return this.price.compareTo(price) > 0;
    }


    public boolean findSameEngineType(EngineType engineType) {


        return engine.type.equals(engineType);
    }

    public boolean findSameBodyType(CarBodyType carBodyType) {

        return carBody.bodyType.equals(carBodyType);
    }

    public boolean doseContainAllComponents(List<String> components) {

        return carBody.components.containsAll(components);
    }


}
