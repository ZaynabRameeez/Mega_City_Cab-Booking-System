/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author zainr
 */



public class Booking {
    private int bookingId;
    private int userId;
    private String pickupLocation;
    private String dropoffLocation;
    private String vehicleType;
    private String rideDate;
    private String rideTime;
    private double price;
    private String status;

    public Booking(int userId, String pickupLocation, String dropoffLocation, String vehicleType, String rideDate, String rideTime, double price) {
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.vehicleType = vehicleType;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.price = price;
        this.status = "Pending";
    }

    // Getters & Setters
    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public String getVehicleType() { return vehicleType; }
    public String getRideDate() { return rideDate; }
    public String getRideTime() { return rideTime; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setStatus(String status) { this.status = status; }
}
