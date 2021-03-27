package com.carsTwo.service;

import com.carsTwo.exception.CarsServiceException;
import com.carsTwo.model.Car;
import com.carsTwo.model.CarBody;
import com.carsTwo.model.Engine;
import com.carsTwo.model.Wheel;
import com.carsTwo.model.enums.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.withinPercentage;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
class CarsServiceTest {



    private final CarsService carsService;


    @BeforeAll
    public static void setUp(){

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


        Set<Car> cars = new HashSet<>();

        cars.add(carOne);
        cars.add(carTwo);
        cars.add(carThree);

        carsService = new CarsService(cars);

    }

    @Test
    @DisplayName("when group correctly be body type and given price range")
    void testOne(){


        var bodyType = CarBodyType.COMBI;
        var minPrice = BigDecimal.valueOf(50000);
        var maxPrice = BigDecimal.valueOf(300000);
        var grouped = carsService.sortByBodyTypeAndPriceRange(bodyType,minPrice,maxPrice);


        assertThat(grouped).size().isEqualTo(1);
        assertThat(grouped.stream().collect(Collectors.toList()).get(0)).isEqualTo("Audi");

    }
    @Test
    @DisplayName("when minimal price is higher than max price")
    void testTwo(){

        assertThrows(CarsServiceException.class, () -> {
            var bodyType = CarBodyType.COMBI;
            var minPrice = BigDecimal.valueOf(5000000);
            var maxPrice = BigDecimal.valueOf(300000);
            var grouped = carsService.sortByBodyTypeAndPriceRange(bodyType,minPrice,maxPrice);
        });
    }

    @Test
    @DisplayName("When any of arguments is null")
    void testThree(){


        var bodyType = CarBodyType.COMBI;
        var minPrice = BigDecimal.valueOf(50000);
        var maxPrice = BigDecimal.valueOf(300000);

        assertThrows(CarsServiceException.class, () -> {


            var grouped = carsService.sortByBodyTypeAndPriceRange(null,minPrice,maxPrice);
        });

        assertThrows(CarsServiceException.class, () -> {

            var grouped = carsService.sortByBodyTypeAndPriceRange(bodyType,null,maxPrice);
        });

        assertThrows(CarsServiceException.class, () -> {

            var grouped = carsService.sortByBodyTypeAndPriceRange(bodyType,minPrice,null);
        });
    }

    @Test
    @DisplayName("when cars are grouped correctly by engine model and sorted alphabetically")
    void testFour(){

        var engineType = EngineType.DIESEL;
        var sorted = carsService.sortByEngineModelWithAlphabeticalOrder(engineType);

        assertThat(sorted).size().isEqualTo(2);
        assertThat(sorted.stream().collect(Collectors.toList()).get(1).sa).isEqualByComparingTo(BigDecimal.valueOf(200000));
        assertThat(sorted.stream().allMatch(p -> p.getEngine().getType().equals(engineType))).isEqualTo(true);
    }

    @Test
    @DisplayName("when engine type is null")
    void testFive(){

        assertThrows(NullPointerException.class, () -> {

            var grouped = carsService.sortByEngineModelWithAlphabeticalOrder(null);
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




    






}