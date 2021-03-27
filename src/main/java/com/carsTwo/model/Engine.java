package com.carsTwo.model;

import com.carsTwo.model.enums.EngineType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Engine {

    EngineType type;
    double power;





}
