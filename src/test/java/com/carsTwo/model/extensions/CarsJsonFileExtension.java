package com.carsTwo.model.extensions;


import com.carsTwo.model.Car;
import com.carsTwo.model.CarBody;
import com.carsTwo.model.Engine;
import com.carsTwo.model.Wheel;
import com.carsTwo.model.enums.CarBodyColor;
import com.carsTwo.model.enums.CarBodyType;
import com.carsTwo.model.enums.EngineType;
import com.carsTwo.model.enums.TyreType;
import com.carsTwo.service.CarsService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class CarsJsonFileExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarsService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {


        Engine engineOne = Engine
                .builder()
                .power(300)
                .type(EngineType.DIESEL)
                .build();

        Engine engineTwo = Engine
                .builder()
                .power(200)
                .type(EngineType.GASOLINE)
                .build();


        CarBody carBodyOne = CarBody
                .builder()
                .bodyType(CarBodyType.COMBI)
                .color(CarBodyColor.GREEN)
                .components(List.of("ABS", "AirCondition", "TV"))
                .build();

        CarBody carBodyTwo = CarBody
                .builder()
                .bodyType(CarBodyType.HATCHBACK)
                .color(CarBodyColor.SILVER)
                .components(List.of("4x4", "AirCondition", "TV"))
                .build();

        CarBody carBodyThree = CarBody
                .builder()
                .bodyType(CarBodyType.COMBI)
                .color(CarBodyColor.WHITE)
                .components(List.of("ABS", "AirCondition", "Electric shields", "6 gears"))
                .build();

        Wheel wheelOne = Wheel
                .builder()
                .model("Debica")
                .size(16)
                .tyreType(TyreType.SUMMER)
                .build();

        Wheel wheelTwo = Wheel
                .builder()
                .model("Dunlope")
                .size(17)
                .tyreType(TyreType.WINTER)
                .build();


        Car carOne = Car
                .builder()
                .mileage(25000)
                .price(new BigDecimal("100000"))
                .model("Audi")
                .carBody(carBodyOne)
                .engine(engineTwo)
                .wheel(wheelOne)
                .build();

        Car carTwo = Car
                .builder()
                .mileage(5000)
                .price(new BigDecimal("200000"))
                .model("BMW")
                .carBody(carBodyTwo)
                .engine(engineOne)
                .wheel(wheelTwo)
                .build();

        Car carThree = Car
                .builder()
                .mileage(50000)
                .price(new BigDecimal("500000"))
                .model("MERCEDES")
                .carBody(carBodyThree)
                .engine(engineOne)
                .wheel(wheelOne)
                .build();


        return new CarsService(Set.of(carOne,carTwo,carThree));
    }
}
