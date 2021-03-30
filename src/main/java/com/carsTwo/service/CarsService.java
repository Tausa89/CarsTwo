package com.carsTwo.service;

import com.carsTwo.exception.CarsServiceException;
import com.carsTwo.model.*;
import com.carsTwo.model.enums.*;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CarsService {


    private Set<Car> cars;

    public CarsService(Set<Car> cars) {
        this.cars = cars;
    }


    /**
     *
     * Method allowed to sorting collection by given Sorting Type with required order descending
     * or ascending
     * @param sortingType Enum decide according to what collection should be sorted.
     * @param descending boolean decide about sorting order.
     * @return Set sorted by given parameters
     */
    public List<Car> sortingByGivenOrder(SortingType sortingType, boolean descending) {

        if (Objects.isNull(sortingType)) {
            throw new CarsServiceException("Sorting type is null");
        }

        Stream<Car> sortedCars =
                switch (sortingType) {
                    case WHEEL_SIZE -> cars.stream().sorted(Comparator.comparing(CarUtils.toWheel.andThen(WheelUtils.toSize)));
                    case ENGINE_POWER -> cars.stream().sorted(Comparator.comparing(CarUtils.toEngine.andThen(EngineUtils.toPower)));
                    case COMPONENT -> cars.stream().sorted(Comparator.comparing(CarUtils.toCarBody.andThen(CarBodyUtils.toAmountOfComponents)));
                };

        var sortedCarList = sortedCars.collect(Collectors.toList());

        if(descending){
            Collections.reverse(sortedCarList);
        }

        return sortedCarList;


    }


    /**
     * Method allowed to select from given collection cars with specified BodyTyp in given price range.
     * @param carBodyType Enum according to which cars are selected
     * @param minPrice BigDecimal the lowest price range
     * @param maxPrice BigDecimal the highest price range
     * @return Set of cars with specified body typ and price higher than minimal price and lower than maximal price
     */
    public Set<Car> groupBySpecifiedBodyTypeWithGivenPriceRange(CarBodyType carBodyType, BigDecimal minPrice, BigDecimal maxPrice) {

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
                .filter(c -> c.findSameBodyType(carBodyType))
                .filter(car -> car.hasPriceGreaterThan(minPrice)  && !car.hasPriceGreaterThan(maxPrice))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    /**
     * Method select from whole collection cars with specified Engine type and sort them alphabetically
     * @param engineType Enum according to which cars are selected
     * @return Set of cars with specified engine alphabetically sorted
     */
    public Set<Car> groupByGivenEngineTypeWithAlphabeticalOrder(EngineType engineType) {


        if(Objects.isNull(engineType)){
            throw new CarsServiceException("Engine type is null");
        }

        return cars
                .stream()
                .filter(c -> c.findSameEngineType(engineType))
                .sorted(CarUtils.compareByModel)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    /**
     * Method provide base statistic like minimal, maximal or average value for whole collection taking as a argument
     * parameter for which on statistic should be counted.
     *
     * @param statisticAttribute Enum decide which statistic should be counted.
     * @return cars statistics for given as parameter required attribute.
     */
    public CarStatistic getStatisticByGivenAttribute(StatisticAttribute statisticAttribute){

        if(Objects.isNull(statisticAttribute)){
            throw new CarsServiceException("Statistic attribute is null");
        }

        return switch (statisticAttribute) {
            case PRICE -> getPriceStatistic();
            case MILEAGE -> getMileageStatistic();
            case ENGINE_POWER -> getEnginePowerStatistic();
        };
    }


    private CarStatistic getPriceStatistic() {

        BigDecimalSummaryStatistics priceStats = cars
                .stream()
                .collect(Collectors2
                        .summarizingBigDecimal(CarUtils.toPrice::apply));

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
                .collect(Collectors.summarizingDouble(CarUtils.toMileage::apply));

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
                .collect(Collectors.summarizingDouble(CarUtils.toEngine.andThen(EngineUtils.toPower)::apply));

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

    /**
     * Method provided mileage for evey single car in collection
     * @return Map with car as a key and value of mileage for every car.
     */

    public Map<Car, Double> getMileageForEveryCar() {

        return cars
                .stream()
                .collect(Collectors.toMap(c -> c, CarUtils.toMileage))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
    }


    /**
     * Method group cars by type of tyre they have
     * @return Map with type of tyre as a key and List of cars having this tyre as value.
     */
    public Map<TyreType, List<Car>> getCarsWithThisSameWheelType() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(CarUtils.toWheel.andThen(WheelUtils.toTyreType)))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));

    }

    /**
     * Method allowed to find a cars with specified components list.
     * @param components List of components that car should have
     * @return Set of cars having all components given as parameter.
     */

    public Set<Car> findAllWithComponents(List<String> components) {

        if(components == null){
            throw new CarsServiceException("Components list is null");
        }
        return cars
                .stream()
                .filter(car -> car.doseContainAllComponents(components))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


}
