package com.carsTwo.model;

import com.carsTwo.model.enums.EngineType;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Engine {

    private EngineType type;
    private double power;
}
