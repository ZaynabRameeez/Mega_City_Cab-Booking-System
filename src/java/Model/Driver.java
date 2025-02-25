/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Driver {
    private int driver_id;
    private int id;   // Foreign key from users table
    private String licenseNumber;
    private String vehicleType;
    private String vehicleNumber;
    private boolean is_approved;
    private String status; // Available, Busy, Offline
    private int totalRides;
    private double earnings;
    private double rating;

    // Constructor for Driver Registration
    public Driver(int id,String licenseNumber, String vehicleType, String vehicleNumber) {
        this.id=id;
        this.licenseNumber = licenseNumber;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.is_approved = false;
        this.status = "Available";
        this.totalRides = 0;
        this.earnings = 0.0;
        this.rating = 0.0;
    }

    // Constructor for fetching driver data
    public Driver(int driver_id, int id,String licenseNumber, String vehicleType, String vehicleNumber, 
                  boolean is_approved, String status, int totalRides, double earnings, double rating) {
        this.driver_id = driver_id;
        this.id= id;
        this.licenseNumber = licenseNumber;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.is_approved = is_approved;
        this.status = status;
        this.totalRides = totalRides;
        this.earnings = earnings;
        this.rating = rating;
    }

    // Getters and Setters
    public int getDriverId() {
        return driver_id;
    }

    public void setDriverId(int driverId) {
        this.driver_id = driverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

  

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(int totalRides) {
        this.totalRides = totalRides;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
