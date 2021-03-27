package com.carsTwo.model;

import com.carsTwo.model.enums.TyreType;

import java.util.Comparator;
import java.util.function.Function;

public interface WheelUtils {




    Comparator<Car> compareBySize = Comparator.comparing(CarUtils.toWheel.andThen(WheelUtils.toSize));


    Function<Wheel, String> toModel = wheel -> wheel.model;
    Function<Wheel, Integer> toSize = wheel -> wheel.size;
    Function<Wheel, TyreType> toTyreType = wheel -> wheel.tyreType;
}
