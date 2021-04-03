package service;

import dao.DriverDao;
import dao.PassengerDao;
import dao.TripDao;
import model.entities.Driver;
import model.entities.Passenger;
import model.entities.Trip;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AgencyService {

    public static UserService userService = new UserService();

    public static void printAllTrips (){
        List<Trip> onGoingTrips = TripDao.fetchAllTrips();
        for(Iterator iterator = onGoingTrips.iterator(); iterator.hasNext();){
            Trip t = (Trip) iterator.next();
            System.out.println(t.toString()+"\n");
        }
    }

    public static void printAllDrivers(){
        List<Driver> allDrivers = DriverDao.fetchAllDrivers();
        for(Iterator iterator = allDrivers.iterator(); iterator.hasNext();){
            Driver d = (Driver) iterator.next();
            System.out.println(d.toString()+"\n");
        }
    }

    public static void printAllPassengers(){
        List<Passenger> allPassengers = PassengerDao.fetchAllPassengers();
        for(Iterator iterator = allPassengers.iterator(); iterator.hasNext();){
            Passenger p = (Passenger) iterator.next();
            System.out.println(p.toString()+"\n");
        }
    }

    public static Driver findOptimumDriver(Passenger passenger){
        List<Driver> allDrivers = DriverDao.fetchAllDrivers();
        List<Double> distances =allDrivers.stream().map(driver ->Math.sqrt(((Math.pow(driver.getLocation_latitude()- passenger.getLocation_latitude(), 2))+Math.pow(driver.getLocation_longitude()- passenger.getLocation_longitude(),2)))).collect(Collectors.toList());
        Double min = Collections.min(distances);
        OptionalInt index = IntStream.range(0, allDrivers.size())
                .filter(i -> min == distances.get(i))
                .findFirst();
        if(index.isPresent() && ! allDrivers.get(index.getAsInt()).isTripState()){
            Driver found = allDrivers.get(index.getAsInt());
            found.setTripState(true);
            passenger.setTripState(true);
            if(DriverDao.updateInfo(found) && PassengerDao.updateInfo(passenger)){
                return found ;
            }
        }
        return null;
    }

}
