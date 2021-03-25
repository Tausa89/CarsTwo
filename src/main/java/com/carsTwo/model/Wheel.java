package com.carsTwo.model;

import com.carsTwo.model.enums.TyreType;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Wheel {

    private String model;
    private int size;
    private TyreType tyreType;
}
