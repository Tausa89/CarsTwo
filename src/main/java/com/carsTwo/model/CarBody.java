package com.carsTwo.model;

import com.carsTwo.model.enums.CarBodyColor;
import com.carsTwo.model.enums.CarBodyType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CarBody {


    CarBodyColor color;
    CarBodyType bodyType;

    @Builder.Default
    List<String> components = new ArrayList<>();
}
