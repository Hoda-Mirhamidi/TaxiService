package model.entities;

import enums.UserRole;
import model.mappedSuperClass.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Driver extends User {

    public Driver(String name, String id, String email, String password, String phone) {
        super(name, id, email, password, phone);
        setUserRole(UserRole.DRIVER);
    }
    @OneToOne
    @JoinColumn
    Vehicle vehicle;

    public Driver() {
        super();
    }

    public String toString(){
        return "Id : "+getId()+" , Username : "+ getUserName()+" , Email : "+getEmail()+" , Phone : "+getPhone();
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }
}
