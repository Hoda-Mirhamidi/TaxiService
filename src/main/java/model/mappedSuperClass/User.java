package model.mappedSuperClass;

import enums.UserRole;

import javax.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    private String id;
    @Column(unique = true)
    private String userName;
    private String email;
    private String password;
    private String phone;
    private UserRole userRole;
    private boolean tripState;
    private Double location_longitude;
    private Double location_latitude;

    public User() {

    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public User(String userName, String id, String email, String password, String phone) {
        this.userName = userName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isTripState() {
        return tripState;
    }

    public void setTripState(boolean tripState) {
        this.tripState = tripState;
    }

    public Double getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(Double location_longitude) {
        this.location_longitude = location_longitude;
    }

    public Double getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(Double location_latitude) {
        this.location_latitude = location_latitude;
    }
}
