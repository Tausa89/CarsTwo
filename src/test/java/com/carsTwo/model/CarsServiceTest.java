package com.carsTwo.model;


import com.carsTwo.exception.CarsServiceException;

import com.carsTwo.model.enums.*;
import com.carsTwo.model.extensions.CarsJsonFileExtension;
import com.carsTwo.service.CarsService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;


import static org.assertj.core.api.Assertions.withinPercentage;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(CarsJsonFileExtension.class)
@RequiredArgsConstructor
class CarsServiceTest {



    private final CarsService carsService;


    @Test
    @DisplayName("when group correctly be body type and given price range")
    void testOne(){


        var bodyType = CarBodyType.COMBI;
        var minPrice = BigDecimal.valueOf(50000);
        var maxPrice = BigDecimal.valueOf(300000);
        var grouped = carsService.groupBySpecifiedBodyTypeWithGivenPriceRange(bodyType,minPrice,maxPrice);


        assertThat(grouped).size().isEqualTo(1);
        assertThat(grouped.stream().findFirst().orElseThrow().model).isEqualTo("Audi");

    }
    @Test
    @DisplayName("when minimal price is higher than max price")
    void testTwo(){

        assertThrows(CarsServiceException.class, () -> {
            var bodyType = CarBodyType.COMBI;
            var minPrice = BigDecimal.valueOf(5000000);
            var maxPrice = BigDecimal.valueOf(300000);
            carsService.groupBySpecifiedBodyTypeWithGivenPriceRange(bodyType,minPrice,maxPrice);
        });
    }

    @Test
    @DisplayName("When any of arguments is null")
    void testThree(){


        var bodyType = CarBodyType.COMBI;
        var minPrice = BigDecimal.valueOf(50000);
        var maxPrice = BigDecimal.valueOf(300000);

        assertThrows(CarsServiceException.class, () -> {


            carsService.groupBySpecifiedBodyTypeWithGivenPriceRange(null,minPrice,maxPrice);
        });

        assertThrows(CarsServiceException.class, () -> {

           carsService.groupBySpecifiedBodyTypeWithGivenPriceRange(bodyType,null,maxPrice);
        });

       assertThrows(CarsServiceException.class, () -> {

            carsService.groupBySpecifiedBodyTypeWithGivenPriceRange(bodyType,minPrice,null);
        });
    }

    @Test
    @DisplayName("when cars are grouped correctly by engine model and sorted alphabetically")
    void testFour(){

        var engineType = EngineType.DIESEL;
        var sorted = carsService.groupByGivenEngineTypeWithAlphabeticalOrder(engineType);

        assertThat(sorted).size().isEqualTo(2);
        assertThat(sorted.stream().findFirst().orElseThrow().price).isEqualByComparingTo(BigDecimal.valueOf(200000));
        assertThat(sorted.stream().allMatch(p -> p.engine.type.equals(engineType))).isEqualTo(true);
    }

    @Test
    @DisplayName("when engine type is null")
    void testFive(){

        assertThrows(CarsServiceException.class, () -> {

            carsService.groupByGivenEngineTypeWithAlphabeticalOrder(null);
        });
    }

    @Test
    @DisplayName("when correctly count statistics")
    void testSix(){

        var statsAttribute = StatisticAttribute.PRICE;
        var statistic = carsService.getStatisticByGivenAttribute(statsAttribute);

        assertThat(statistic.getPrice().getMax()).isEqualTo(BigDecimal.valueOf(500000));
        assertThat(statistic.getPrice().getAverage()).isCloseTo(BigDecimal.valueOf(266666), withinPercentage(1));
    }


    @Test
    @DisplayName("when sorted correctly by engine power with descending order")
    void testSeven(){

        var sorted = carsService.sortingByGivenOrder(SortingType.ENGINE_POWER,true);

        assertThat(sorted.stream().findFirst().orElseThrow().engine.power).isEqualTo(300);
    }

    @Test
    @DisplayName("when sorted correctly by engine power with ascending order")
    void testEight(){

        var sorted = carsService.sortingByGivenOrder(SortingType.ENGINE_POWER,false);

        assertThat(sorted.stream().findFirst().orElseThrow().engine.power).isEqualTo(200);
    }


    @Test
    @DisplayName("when sorted correctly by wheel size with descending order")
    void testNine(){

        var sorted = carsService.sortingByGivenOrder(SortingType.WHEEL_SIZE,true);

        assertThat(sorted.stream().findFirst().orElseThrow().wheel.size).isEqualTo(17);
    }

    @Test
    @DisplayName("when sorted correctly by wheel size with ascending order")
    void testTen(){

        var sorted = carsService.sortingByGivenOrder(SortingType.WHEEL_SIZE,false);

        assertThat(sorted.stream().findFirst().orElseThrow().wheel.size).isEqualTo(16);
    }


    @Test
    @DisplayName("when sorted correctly by amount of components with ascending order")
    void testEleven(){

        var sorted = carsService.sortingByGivenOrder(SortingType.COMPONENT,false);

        assertThat(sorted.stream().findFirst().orElseThrow().carBody.components).size().isEqualTo(3);
    }


    @Test
    @DisplayName("when sorted correctly by amount of components with descending order")
    void testTwelve(){

        var sorted = carsService.sortingByGivenOrder(SortingType.COMPONENT,true);

        assertThat(sorted.stream().findFirst().orElseThrow().carBody.components).size().isEqualTo(4);
    }


    @Test
    @DisplayName("when show correctly mileage for every car")
    void testThirteen(){

        var sorted = carsService.getMileageForEveryCar();


        //ToDo how to compare keys.
        assertThat(sorted)
                .hasSize(3)
                .containsValues(25000D, 50000D, 5000D);
    }

    @Test
    @DisplayName("when cars are grouped correctly by wheel type they have")
    void testFourteen(){

        var sorted = carsService.getCarsWithThisSameWheelType();

        assertThat(sorted.values().stream().findFirst().orElseThrow().size()).isEqualTo(2);
        assertThat(sorted)
                .containsKeys(TyreType.SUMMER,TyreType.WINTER)
                .hasSize(2);

    }

    @Test
    @DisplayName("when find correctly cars with given components")
    void testFifteen(){


        var components = List.of("ABS", "AirCondition", "Electric shields", "6 gears");

        var sorted = carsService.findAllWithComponents(components);

        assertThat(sorted)
                .hasSize(1);
        assertThat(sorted.stream().findFirst().orElseThrow().model).isEqualTo("MERCEDES");
    }


    @Test
    @DisplayName("when components list is null")
    void testSixteen(){



        assertThrows(CarsServiceException.class, ()->{
            carsService.findAllWithComponents(null);
        });



    }














}