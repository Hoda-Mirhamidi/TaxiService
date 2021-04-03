package model.entities;

import enums.UserRole;
import model.mappedSuperClass.User;

import javax.persistence.Entity;


@Entity
public class Passenger extends User {

    private double credit;

    public Passenger(String name, String id, String email, String password, String phone) {
        super(name, id, email, password, phone);
        setUserRole(UserRole.PASSENGER);
    }

    public Passenger() {

    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Username : "+this.getUserName()+" , Id : "+this.getId()+" , Email : "+this.getEmail()+" , Phone : "+this.getPhone()+" , Credit : "+this.credit;
    }
}
