package com.carsTwo.model;

import com.carsTwo.model.enums.CarBodyColor;
import com.carsTwo.model.enums.CarBodyType;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CarBody {


    private CarBodyColor color;
    private CarBodyType bodyType;
    private List<String> components;
}
