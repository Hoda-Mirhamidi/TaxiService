import dao.DriverDao;
import dao.PassengerDao;
import dao.TripDao;
import dao.VehicleDao;
import model.entities.Driver;
import model.entities.Passenger;
import model.entities.Trip;
import model.entities.Vehicle;
import service.AgencyService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String name, password;
        while (true){
            System.out.println("1) Add a group of drivers : ");
            System.out.println("2) Add a group of passengers : ");
            System.out.println("3) Driver sign up or login : ");
            System.out.println("4) Passenger sign up or login : ");
            System.out.println("5) Show ongoing trip : ");
            System.out.println("6) Show list of drivers : ");
            System.out.println("7) Show list of passengers : ");
            Scanner scInt = new Scanner(System.in);
            Scanner scString = new Scanner(System.in);
            Scanner scDouble = new Scanner(System.in);
            int choice = scInt.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the number of drivers : ");
                    int driverCount = scInt.nextInt();
                    for (int i = 0; i < driverCount; i++) {
                        System.out.println("Please enter driver's name , id , email , password and phone respectively (separate data by spaces) : ");
                        String commandLine = scString.nextLine();
                        String[] data = commandLine.split("\\s+");
                        boolean inputIsOK = false;
                        try {
                            Driver addedDriver = DriverDao.addDriver((Driver) AgencyService.userService.signUp(data[0], data[1], data[2], data[3], data[4], 1));
                            if(addedDriver != null){
                                while(!inputIsOK){
                                    System.out.println("Enter the driver's vehicle plate , model , color and type respectively (separate data by spaces) : ");
                                    String infoLine = scString.nextLine();
                                    String[] vehicleInfo = infoLine.split("\\s+");
                                    try{
                                        Vehicle addedVehicle = VehicleDao.addVehicle(AgencyService.userService.vehicleRegistration(addedDriver,vehicleInfo[0],vehicleInfo[1],vehicleInfo[2],vehicleInfo[3]));
                                        addedDriver.setVehicle(addedVehicle);
                                        DriverDao.updateInfo(addedDriver);
                                        inputIsOK = true;
                                    } catch (Exception exception) {
                                        System.out.println("\nVehicle info error occurred ! Please try again... ");
                                    }
                                }
                            }
                        } catch (Exception exception) {
                            driverCount++;
                            System.out.println("\nDriver info error occurred ! Please try again... ");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter the number of passengers : ");
                    int passengerCount = scInt.nextInt();
                    for (int i = 0; i < passengerCount; i++) {
                        System.out.println("Please enter passenger's name , id , email , password and phone respectively (separate data by spaces) : ");
                        String commandLine = scString.nextLine();
                        String[] data = commandLine.split("\\s+");
                        try {
                            PassengerDao.addPassenger((Passenger) AgencyService.userService.signUp(data[0], data[1], data[2], data[3], data[4], 2));
                        } catch (Exception exception) {
                            passengerCount++;
                            System.out.println("\n Passenger info error occurred ! Please try again... ! ");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter username : ");
                    name = scString.nextLine();
                    Driver d = DriverDao.lookUp(name);
                    if (d == null) {
                        System.out.println("1) Register " + "\n" + "2) Exit");
                        int option = scInt.nextInt();
                        switch (option) {
                            case 1:
                                System.out.println("***** REGISTER NEW DRIVER *****");
                                boolean inputIsOk = false;
                                while (!inputIsOk) {
                                    System.out.println("Please enter driver's name , id , email , password and phone respectively (separate data by spaces) : ");
                                    String commandLine = scString.nextLine();
                                    String[] data = commandLine.split("\\s+");
                                    boolean vehicleInput = false;
                                    try{
                                        Driver newDriver = DriverDao.addDriver((Driver) AgencyService.userService.signUp(data[0],data[1],data[2],data[3],data[4],1));
                                        if(newDriver != null){
                                            System.out.println("*** SET VEHICLE ***");
                                            while(!vehicleInput){
                                                System.out.println("Enter the driver's vehicle plate , model , color and type respectively (separate data by spaces) : ");
                                                String infoLine = scString.nextLine();
                                                String[] vehicleInfo = infoLine.split("\\s+");
                                                try{
                                                    Vehicle addedVehicle = VehicleDao.addVehicle(AgencyService.userService.vehicleRegistration(newDriver,vehicleInfo[0],vehicleInfo[1],vehicleInfo[2],vehicleInfo[3]));
                                                    newDriver.setVehicle(addedVehicle);
                                                    DriverDao.updateInfo(newDriver);
                                                    vehicleInput = true;
                                                } catch (Exception exception) {
                                                    exception.printStackTrace();
                                                }
                                            }
                                            inputIsOk = true;
                                        }
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            case 2:
                                System.exit(0);
                        }
                        break;
                    } else {
                        System.out.println("*** Login page ***");
                        System.out.println("Enter password : ");
                        password = scString.nextLine();
                        if(d.getPassword().equals(password)){
                            if(d.isTripState()){
                                System.out.println("You're on a trip at the moment ! + \n****** OPTIONS ******");
                                System.out.println("\n1) Confirm passenger's payment : + \n2) End trip \n3) Exit ");
                                int option = scInt.nextInt();
                                if (option == 1){
                                    Trip tripAtTheMoment = TripDao.lookUp(d);
                                    tripAtTheMoment.setPayment_check(true);
                                    TripDao.updateInfo(tripAtTheMoment);
                                }
                                else if (option == 2) {
                                    if(AgencyService.userService.cancelTripByDriver(d)){
                                        System.out.println("Trip Ended Successfully ! ");
                                    }else{
                                        System.out.println("An error has occurred ! Please try again ...");
                                    }
                                    break;
                                } else if (option == 3) {
                                    System.exit(0);
                                }
                            }

                        }
                        else{
                            System.out.println("You are on wait !+\n****** OPTIONS ******");
                            System.out.println("1) Back to main menu : +\n2) Exit");
                            int option = scInt.nextInt();
                            if(option==1){
                                break;
                            }
                            else if(option==2){
                                System.exit(0);
                            }
                            }
                        }
                    break;
                case 4:
                    System.out.println("Enter username : ");
                    name = scString.nextLine();
                    Passenger p = PassengerDao.lookUp(name);
                    if (p == null) {
                        System.out.println("1) Register " + "\n" + "2) Exit");
                        int option = scInt.nextInt();
                        switch (option) {
                            case 1:
                                boolean inputIsOk = false;
                                while(!inputIsOk) {
                                    try{
                                        System.out.println("Please enter passenger's name , id , email , password and phone respectively (separate data by spaces) : ");
                                        String commandLine = scString.nextLine();
                                        String[] data = commandLine.split("\\s+");
                                        PassengerDao.addPassenger((Passenger) AgencyService.userService.signUp(data[0], data[1], data[2], data[3], data[4],2));
                                        inputIsOk = true;
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                        System.out.println("\nPassenger info error occurred ! Please try again... ! ");
                                    }
                                }
                            case 2:
                                System.exit(0);
                        }
                    } else {
                        System.out.println("*** Login page ***");
                        System.out.println("Enter password : ");
                        password = scString.nextLine();
                        if(p.getPassword().equals(password)){
                            if( !p.isTripState()){
                                boolean inputIsOk = false;
                                while (!inputIsOk) {
                                    System.out.println("1) Request new trip (pay with cash) : ");
                                    System.out.println("2) Request new trip (pay with credit) : ");
                                    System.out.println("3) Increase credit : ");
                                    System.out.println("4) Exit : ");
                                    int option = scInt.nextInt();
                                    if (option == 1 || option == 2) {
                                        System.out.println("Please enter the origin's longitude : ");
                                        Double originLongitude = scDouble.nextDouble();
                                        System.out.println("Please enter the origin's latitude : ");
                                        Double originLatitude = scDouble.nextDouble();
                                        System.out.println("Please enter the destination's longitude : ");
                                        Double destinationLongitude = scDouble.nextDouble();
                                        System.out.println("Please enter the destination's latitude : ");
                                        Double destinationLatitude = scDouble.nextDouble();
                                        Trip trip = new Trip(originLongitude, originLatitude, destinationLongitude, destinationLatitude, p);
                                        System.out.println(trip.payPerUnitOfDistance);
                                        System.out.println(trip.getDestination_latitude());
                                        System.out.println(trip.getDestination_longitude());
                                        System.out.println(trip.getOrigin_latitude());
                                        System.out.println(trip.getOrigin_longitude());
                                        System.out.println(trip.getDistance());
                                        double cost = trip.getDistance() * trip.payPerUnitOfDistance;
                                        if (option == 2) {
                                            if (p.getCredit() < cost) {
                                                System.out.println("Sorry ! You dont have enough credit for this trip !");
                                                break;
                                            }
                                            else{
                                                p.setCredit(p.getCredit()-cost);
                                                PassengerDao.updateInfo(p);
                                            }
                                        }
                                        try {
                                            Driver found = AgencyService.findOptimumDriver(p);
                                            if (found != null) {
                                                System.out.println("Driver found ! " + found.toString());
                                                trip.setDriver(found);
                                                TripDao.addTrip(trip);
                                                inputIsOk = true;
                                            }
                                        } catch (RuntimeException exception) {
                                            System.out.println("No Such driver was found ! ");
                                        }
                                    }
                                    else if(option ==3){
                                        System.out.println("Enter the amount you want to increase your credit by : ");
                                        Double increaseAmount = scDouble.nextDouble();
                                        AgencyService.userService.increaseCredit(p,increaseAmount);
                                        inputIsOk=true;
                                    }
                                    else{
                                        System.exit(0);
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    AgencyService.printAllTrips();
                    break;
                case 6:
                    AgencyService.printAllDrivers();
                    break;
                case 7:
                    AgencyService.printAllPassengers();
                    break;
            }
        }
    }
}
