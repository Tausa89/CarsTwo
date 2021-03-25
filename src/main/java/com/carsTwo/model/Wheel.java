package com.carsTwo.model;

import com.carsTwo.model.enums.TyreType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Wheel {

    String model;
    int size;
    TyreType tyreType;
}
