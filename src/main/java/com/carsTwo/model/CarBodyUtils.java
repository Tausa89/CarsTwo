package com.carsTwo.model;

import com.carsTwo.model.enums.CarBodyColor;
import com.carsTwo.model.enums.CarBodyType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface CarBodyUtils {


    Function<CarBody, CarBodyColor> toBodyColor = carBody -> carBody.color;
    Function<CarBody, CarBodyType> toBodyType = carBody -> carBody.bodyType;
    Function<CarBody, List<String>> toComponents = carBody -> new ArrayList<>(carBody.components);
}
