/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Service;

import DAO.DriverDAO;
import Model.Driver;
import Model.Ride;
import java.util.List;

public class DriverService {
    private DriverDAO driverDAO;

    public DriverService() {
        this.driverDAO = new DriverDAO();
    }

    // ✅ Register a new driver
    public boolean registerDriver(Driver driver) {
        return driverDAO.registerDriver(driver);
    }

//    // ✅ Update driver status (Active, Offline, Busy)
//    public boolean updateDriverStatus(int driverId, String status) {
//        return driverDAO.updateDriverStatus(driverId, status);
//    }
//
//    // ✅ Update driver earnings
//    public boolean updateDriverEarnings(int driverId, double amount) {
//        return driverDAO.updateDriverEarnings(driverId, amount);
//    }
//
//    // ✅ Request driver info update (Requires admin approval)
//    public boolean updateDriverInfo(int driverId, String license, String vehicleType, String vehicleNumber) {
//        return driverDAO.updateDriverInfo(driverId, license, vehicleType, vehicleNumber);
//    }
//
//    // ✅ Get all rides assigned to a driver
//    public List<Ride> getDriverRides(int driverId) {
//        return driverDAO.getDriverRides(driverId);
//    }

//    // ✅ Driver confirms ride
//    public boolean confirmRide(int rideId, int driverId) {
//        return driverDAO.confirmRide(rideId, driverId);
//    }
//
//    // ✅ Get driver status
//    public String getDriverStatus(int driverId) {
//        return driverDAO.getDriverStatus(driverId);
//    }
//
//    // ✅ Assign ride to driver
//    public boolean assignRideToDriver(int rideId, int driverId) {
//        return driverDAO.assignRideToDriver(rideId, driverId);
//    }
}











//package Service;
//
///**
// *
// * @author zainr
// */
//
//
//import DAO.DriverDAO;
//import Model.User;
//import java.util.List;
//
//public class DriverService {
//    private final DriverDAO driverDAO;
//
//    public DriverService() {
//        this.driverDAO = new DriverDAO();
//    }
//
//    // ✅ Get all active drivers
//    public List<User> getActiveDrivers() {
//        return driverDAO.getActiveDrivers();
//    }
//
//    // ✅ Get driver by ID
//    public User getDriverByUserId(int userID ) {
//        return driverDAO.getDriverByUserId(userID);
//    }
//
//    // ✅ Update driver availability status
//    public boolean updateDriverStatus(int driverId, String status) {
//        if (status == null || status.isEmpty()) {
//            System.err.println("Error: Status cannot be empty!");
//            return false;
//        }
//        return driverDAO.updateDriverStatus(driverId, status);
//    }
//
//    // ✅ Update driver vehicle details
//    public boolean updateDriverVehicle(int driverId, String vehicleType, String vehicleNumber, int seatingCapacity) {
//        if (vehicleType == null || vehicleNumber == null || seatingCapacity <= 0) {
//            System.err.println("Error: Invalid vehicle details provided.");
//            return false;
//        }
//        return driverDAO.updateDriverVehicle(driverId, vehicleType, vehicleNumber, seatingCapacity);
//    }
//
//    // ✅ Get driver earnings
//    public double getDriverEarnings(int driverId) {
//        return driverDAO.getDriverEarnings(driverId);
//    }
//
//    // ✅ Get driver ride history
//    public List<String> getDriverRides(int driverId) {
//        return driverDAO.getDriverRides(driverId);
//    }
//    
//  
//    public boolean acceptRide(int driverId, int rideId) {
//        return driverDAO.assignRideToDriver(driverId, rideId);
//    }
//
//    public boolean updateDriverProfile(int driverId, String name, String phone, String vehicleType, String vehicleNumber) {
//        return driverDAO.updateDriverInfo(driverId, name, phone, vehicleType, vehicleNumber);
//    }
//}
//
