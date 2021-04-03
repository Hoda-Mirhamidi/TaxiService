package service;

import dao.DriverDao;
import dao.PassengerDao;
import dao.TripDao;
import enums.VehicleType;
import exceptions.*;
import model.entities.Driver;
import model.entities.Passenger;
import model.entities.Trip;
import model.entities.Vehicle;
import model.mappedSuperClass.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    public User signUp(String name, String id , String email , String password , String phone , int roleNum){
        try{
            if(validate(phone,password,email)){
                switch (roleNum) {
                    case 1 :
                        Driver driver = new Driver(name, id, email, password, phone);
                        driver.setTripState(false);
                        driver.setLocation_latitude(0D);
                        driver.setLocation_longitude(0D);
                        return driver ;
                    case 2 :
                        Passenger passenger = new Passenger(name,id,email,password,phone);
                        passenger.setTripState(false);
                        passenger.setLocation_latitude(0D);
                        passenger.setLocation_longitude(0D);
                        return passenger;
                }
            }
        }catch (WrongPhoneNumberException | PasswordLengthException | InvalidEmailException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }


    public boolean validate(String phoneNumber,String password, String email) throws WrongPhoneNumberException, PasswordLengthException, InvalidEmailException {
        return validatePhoneNumber(phoneNumber) && validatePassword(password) && validateEmail(email);
    }
    private boolean validatePhoneNumber(String phoneNumber) throws WrongPhoneNumberException{
        String regex = "^[0][9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(matcher.matches()){
            return true;
        }
        throw new WrongPhoneNumberException("Invalid phone number ! ");
    }

    private boolean validatePassword(String password) throws PasswordLengthException{
        String regex = "\\d{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(matcher.matches()){
            return true;
        }
        throw new PasswordLengthException("The password needs to have 8 characters at least ! ");
    }

    private boolean validateEmail(String email) throws InvalidEmailException {
        String regex = "^[a-z0-9+_.-]+@[a-z0-9.-]+.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            return true;
        }
        throw new InvalidEmailException("Email is invalid !");
    }


    public Vehicle vehicleRegistration(Driver driver , String plate, String model , String color , String type) throws Exception {
        try{
            VehicleType vehicleType = VehicleType.formValue(type);
            if(validatePlate(plate)){
                switch (vehicleType){
                    case CAR :
                        return new Vehicle(driver,plate,model,color,VehicleType.CAR);
                    case VAN :
                        return new Vehicle(driver,plate,model,color,VehicleType.VAN);
                    case MOTORCYCLE:
                        return new Vehicle(driver,plate,model,color,VehicleType.MOTORCYCLE);
                    case TRUCK:
                        return new Vehicle(driver,plate,model,color,VehicleType.TRUCK);

                }
            }
        }
        catch (VehicleTypeException | InvalidPlateException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private boolean validatePlate(String plate) throws InvalidPlateException{
        String regex = "^[1-9][1-9][a-zA-Z][1-9][1-9][1-9][1-9][0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(plate);
        if(matcher.matches()){
            return true;
        }
        throw new InvalidPlateException("The plate is invalid! ");
    }

    public static boolean login(User user , String password , int roleNum){

        if(roleNum==1 && user.getPassword().equals(password)){
            return true;
        }
        else if(roleNum == 2 && user.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    public boolean cancelTripByDriver(Driver driver){
        Trip trip = TripDao.lookUp(driver);
        Passenger passenger = trip.getPassenger();
        if(TripDao.cancel(trip)){
            driver.setTripState(false);
            passenger.setTripState(false);
            driver.setLocation_latitude(trip.getDestination_latitude());
            driver.setLocation_longitude(trip.getDestination_longitude());
            if(PassengerDao.updateInfo(passenger) && DriverDao.updateInfo(driver)){
                return true;
            }
        }
        return false;
    }

    public boolean increaseCredit(Passenger passenger , Double creditIncrease){
        passenger.setCredit(passenger.getCredit()+creditIncrease);
        if(PassengerDao.updateInfo(passenger)){
            return true;
        }
        return false;
    }

}
