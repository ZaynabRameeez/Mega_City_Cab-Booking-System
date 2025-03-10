/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author zainr
 */


public class Ride {
    private int rideId;
    private int id;
    private int driverId;
    private String pickup;
    private String dropoff;
    private double fare;
    private String status; // Pending, Confirmed, Completed

    public Ride(int rideId, int id, int driverId, String pickup, String dropoff, double fare, String status) {
        this.rideId = rideId;
        this.id = id;
        this.driverId = driverId;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.fare = fare;
        this.status = status;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
