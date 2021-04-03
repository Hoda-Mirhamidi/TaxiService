package model.entities;

import enums.VehicleType;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    private String plate;

    @OneToOne
    private Driver driver;

    private String model;
    private String color;
    VehicleType vehicleType;

    public Vehicle(Driver driver,String plate, String model, String color, VehicleType vehicleType) {
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.vehicleType = vehicleType;
        this.driver = driver;
    }

    public Vehicle() {

    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getPlate() {
        return plate;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
