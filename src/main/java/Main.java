import com.carsTwo.model.Car;
import com.carsTwo.model.CarBody;
import com.carsTwo.model.Engine;
import com.carsTwo.model.Wheel;
import com.carsTwo.model.enums.*;
import com.carsTwo.service.CarsService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {


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

        Set<Car> set = new HashSet<>();

        set.add(carOne);
        set.add(carTwo);
        set.add(carThree);

        CarsService cars = new CarsService(set);

        System.out.println(cars);

        System.out.println("********************************");


        System.out.println(cars.sortingByGivenOrder(SortingType.ENGINE, false));



    }
}
