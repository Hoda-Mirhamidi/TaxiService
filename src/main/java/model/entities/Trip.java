package model.entities;

import javax.persistence.*;

@Entity
public class Trip {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int tripId;

    private Double origin_longitude;
    private Double origin_latitude;
    private Double destination_longitude;
    private Double destination_latitude;

    @OneToOne
    @JoinColumn
    private Driver driver;

    @OneToOne
    @JoinColumn
    private Passenger passenger;

    private Boolean payment_check;
    private Double distance;
    public int payPerUnitOfDistance = 1000;


    public Trip(Double origin_longitude, Double origin_latitude, Double destination_longitude, Double destination_latitude, Passenger passenger) {
        this.origin_longitude = origin_longitude;
        this.origin_latitude = origin_latitude;
        this.destination_longitude = destination_longitude;
        this.destination_latitude = destination_latitude;
        this.passenger = passenger;
    }

    public Trip() {

    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Double getOrigin_longitude() {
        return origin_longitude;
    }

    public void setOrigin_longitude(Double origin_longitude) {
        this.origin_longitude = origin_longitude;
    }

    public Double getOrigin_latitude() {
        return origin_latitude;
    }

    public void setOrigin_latitude(Double origin_latitude) {
        this.origin_latitude = origin_latitude;
    }

    public Double getDestination_longitude() {
        return destination_longitude;
    }

    public void setDestination_longitude(Double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public Double getDestination_latitude() {
        return destination_latitude;
    }

    public void setDestination_latitude(Double destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Boolean getPayment_check() {
        return payment_check;
    }

    public void setPayment_check(Boolean payment_check) {
        this.payment_check = payment_check;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = Math.sqrt(((Math.pow(origin_longitude-destination_longitude,2))+Math.pow(origin_latitude-destination_latitude,2)));
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", origin_longitude=" + origin_longitude +
                ", origin_latitude=" + origin_latitude +
                ", destination_longitude=" + destination_longitude +
                ", destination_latitude=" + destination_latitude +
                ", driver=" + driver +
                ", passenger=" + passenger +
                ", payment_check=" + payment_check +
                ", distance=" + distance +
                '}';
    }
}
