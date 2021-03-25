package com.carsTwo.service;

import com.carsTwo.exception.CarsServiceException;
import com.carsTwo.model.Car;
import com.carsTwo.model.CarStatistic;
import com.carsTwo.model.Statistic;
import com.carsTwo.model.enums.*;
import lombok.*;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CarsService {


    private Set<Car> cars;
    private SortingType sortingType;

    public CarsService(Set<Car> cars) {
        this.cars = cars;
    }

    public List<Car> sortingByGivenOrder(SortingType sortingType, boolean descending) {

        if (Objects.isNull(sortingType)) {
            throw new IllegalStateException("Sorting type is null");
        }

        Stream<Car> sortedCars =
                switch (sortingType) {
                    case WHEEL_SIZE ->cars.stream().sorted(Comparator.comparing(c -> c.getWheel().getSize()));
                    case ENGINE -> cars.stream().sorted(Comparator.comparing(c -> c.getEngine().getPower()));
                    default -> cars.stream().sorted(Comparator.comparing(c -> c.getCarBody().getComponents().size()));
                };

        var sortedCarList = sortedCars.collect(Collectors.toList());

        if(descending){
            Collections.reverse(sortedCarList);
        }

        return sortedCarList;


    }



    public Set<Car> sortByBodyTypeAndPriceRange(CarBodyType carBodyType, BigDecimal minPrice, BigDecimal maxPrice) {

        if(Objects.isNull(carBodyType)){
            throw new CarsServiceException("Body Type can't be null");
        }
        if(Objects.isNull(minPrice)){
            throw new CarsServiceException("Minimal Price can't be null");
        }
        if(Objects.isNull(maxPrice)){
            throw new CarsServiceException("Maximal Price can't be null");
        }

        if(minPrice.compareTo(maxPrice)> 0){
            throw new CarsServiceException("Minimal price can't be higher than maximal price");
        }

        return cars
                .stream()
                .filter(c -> c.getCarBody().getBodyType() == carBodyType)
                .filter(c -> c.getPrice().compareTo(minPrice) > 0 && c.getPrice().compareTo(maxPrice) < 1)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public Set<Car> sortByEngineModelWithAlphabeticalOrder(@NonNull EngineType engineType) {



        return cars
                .stream()
                .filter(c -> c.getEngine().getType() == engineType)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public CarStatistic getStatisticByGivenAttribute(StatisticAttribute statisticAttribute){

        if(statisticAttribute == null){
            throw new IllegalStateException("Statistic attribute is null");
        }

        return switch (statisticAttribute) {
            case PRICE -> getPriceStatistic();
            case MILEAGE -> getMileageStatistic();
            case ENGINE_POWER -> getEnginePowerStatistic();
        };
    }


    private CarStatistic getPriceStatistic() {

        BigDecimalSummaryStatistics priceStats = cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice));

        return CarStatistic
                .builder()
                .price(Statistic
                        .<BigDecimal>builder()
                        .min(priceStats.getMin())
                        .max(priceStats.getMax())
                        .average(priceStats.getAverage())
                        .build())
                .build();
    }

    private CarStatistic getMileageStatistic() {
        DoubleSummaryStatistics mileageStatistics = cars.stream()
                .collect(Collectors.summarizingDouble(Car::getMileage));

        return CarStatistic
                .builder()
                .mileage(Statistic
                        .<Double>builder()
                        .min(mileageStatistics.getMin())
                        .max(mileageStatistics.getMax())
                        .average(mileageStatistics.getAverage())
                        .build())
                .build();

    }

    private CarStatistic getEnginePowerStatistic() {


        DoubleSummaryStatistics powerStatistics = cars.stream()
                .collect(Collectors.summarizingDouble(c -> c.getEngine().getPower()));

        return CarStatistic
                .builder()
                .power(Statistic
                        .<Double>builder()
                        .min(powerStatistics.getMin())
                        .max(powerStatistics.getMax())
                        .average(powerStatistics.getAverage())
                        .build())
                .build();

    }


    public Map<Car, Integer> getMileageForEveryCar() {

        return cars
                .stream()
                .collect(Collectors.toMap(c -> c, Car::getMileage))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
    }


    public Map<TyreType, List<Car>> getCarsWithThisSameWheelType() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(c -> c.getWheel().getTyreType()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));

    }

    public Set<Car> findAllWithComponents(List<String> components) {

        if(components == null){
            throw new IllegalStateException("Components list is null");
        }
        return cars
                .stream()
                .filter(car -> car.getCarBody().getComponents().containsAll(components))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


}
