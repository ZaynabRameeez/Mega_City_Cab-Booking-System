/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Utils.DBConfig;

public class DriverDAO {
   public static boolean registerDriver(int userId, String licenseNumber, String vehicleType, String vehicleNumber) throws SQLException {
    Connection conn = DBConfig.getConnection();
   boolean success = false;  // Declare the variable here


        try {
            // Insert into drivers table
            String driverQuery = "INSERT INTO drivers (user_id, license_number, is_approved, status) VALUES (?, ?, 0, 'Pending')";
            PreparedStatement driverStmt = conn.prepareStatement(driverQuery);
            driverStmt.setInt(1, userId);
            driverStmt.setString(2, licenseNumber);
            int driverResult = driverStmt.executeUpdate();

            // Insert into vehicles table
            String vehicleQuery = "INSERT INTO vehicles (vehicle_number, type_id, driver_id) VALUES (?, (SELECT id FROM vehicle_types WHERE type_name=?), (SELECT driver_id FROM drivers WHERE user_id=?))";
            PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery);
            vehicleStmt.setString(1, vehicleNumber);
            vehicleStmt.setString(2, vehicleType);
            vehicleStmt.setInt(3, userId);
            int vehicleResult = vehicleStmt.executeUpdate();

            if (driverResult > 0 && vehicleResult > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}













//package DAO;
//
//import Model.Driver;
//import Model.Ride;
//import Utils.DBConfig;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DriverDAO {
//
//    // ✅ Register Driver (Insert new driver request)
//    public boolean registerDriver(Driver driver) {
//        String query = "INSERT INTO drivers (id, licenseNumber, vehicleType, vehicleNumber, is_approved, status, totalRides, earnings, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driver.getId());
//            stmt.setString(2, driver.getLicenseNumber());
//            stmt.setString(3, driver.getVehicleType());
//            stmt.setString(4, driver.getVehicleNumber());
//            stmt.setBoolean(5, false);  // Default: Not approved
//            stmt.setString(6, "Pending");
//            stmt.setInt(7, 0);  // Default total rides = 0
//            stmt.setDouble(8, 0.0);  // Default earnings = 0
//            stmt.setDouble(9, 0.0);  // Default rating = 0
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Update Driver Status (Active, Busy, Offline)
//    public boolean updateDriverStatus(int driverId, String status) {
//        String query = "UPDATE drivers SET status = ? WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, status);
//            stmt.setInt(2, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Update Driver Earnings
//    public boolean updateDriverEarnings(int driverId, double amount) {
//        String query = "UPDATE drivers SET earnings = earnings + ? WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setDouble(1, amount);
//            stmt.setInt(2, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Update Driver Info (Requires Admin Approval)
//    public boolean updateDriverInfo(int driverId, String license, String vehicleType, String vehicleNumber) {
//        String query = "UPDATE drivers SET licenseNumber = ?, vehicleType = ?, vehicleNumber = ?, is_approved = false WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, license);
//            stmt.setString(2, vehicleType);
//            stmt.setString(3, vehicleNumber);
//            stmt.setInt(4, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Get All Rides Assigned to a Driver
//    public List<Ride> getDriverRides(int driverId) {
//        List<Ride> rides = new ArrayList<>();
//        String query = "SELECT * FROM rides WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driverId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Ride ride = new Ride(
//                    rs.getInt("ride_id"),
//                    rs.getInt("user_id"),
//                    rs.getInt("driver_id"),
//                    rs.getString("pickup"),
//                    rs.getString("dropoff"),
//                    rs.getDouble("fare"),
//                    rs.getString("status") // e.g., Pending, Completed
//                );
//                rides.add(ride);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rides;
//    }
//
//    // ✅ Confirm Ride (Driver Accepts)
//    public boolean confirmRide(int rideId, int driverId) {
//        String query = "UPDATE rides SET status = 'Accepted' WHERE ride_id = ? AND driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, rideId);
//            stmt.setInt(2, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Get Driver Status
//    public String getDriverStatus(int driverId) {
//        String query = "SELECT status FROM drivers WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driverId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getString("status");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "Unavailable"; // Default
//    }
//
//    // ✅ Assign Ride to Driver
//    public boolean assignRideToDriver(int rideId, int driverId) {
//        String query = "UPDATE rides SET driver_id = ?, status = 'Assigned' WHERE ride_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driverId);
//            stmt.setInt(2, rideId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
//
//
//
//
//
//




























//package dao;
//
//import Model.Driver;
//import Model.Ride;
//import Utils.DBConfig;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DriverDAO {
//
//    // Method to insert a new driver request
//    public boolean requestDriver(Driver driver) {
//        String query = "INSERT INTO drivers (id, license_number, vehicle_type, vehicle_number, is_approved, status, total_rides, earnings,rating) VALUES (?, ?, ?, ?, false, 'Available', 0, 0.00)";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driver.getId());
//            stmt.setString(2, driver.getLicenseNumber());
//            stmt.setString(3, driver.getVehicleType());
//            stmt.setString(4, driver.getVehicleNumber());
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Method to update driver status (Available, Busy, Offline)
//    public boolean updateDriverStatus(int driverId, String status) {
//        String query = "UPDATE drivers SET status = ? WHERE id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, status);
//            stmt.setInt(2, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Method to update driver earnings
//    public boolean updateDriverEarnings(int driverId, double earnings) {
//        String query = "UPDATE drivers SET earnings = earnings + ? WHERE id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setDouble(1, earnings);
//            stmt.setInt(2, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Method to update driver information (name, phone, email, vehicle details)
//    public boolean updateDriverInfo(Driver driver) {
//        String query = "UPDATE drivers SET Username = ?, phone = ?, email = ?, vehicle_type = ?, vehicle_number = ? WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, driver.getUsername());
//            stmt.setString(2, driver.getPhone());
//            stmt.setString(3, driver.getEmail());
//            stmt.setString(4, driver.getVehicleType());
//            stmt.setString(5, driver.getVehicleNumber());
//            stmt.setInt(6, driver.getDriverId());
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Method to get all rides assigned to a driver (ride history)
//    public List<Ride> getDriverRides(int driverId) {
//        List<Ride> rides = new ArrayList<>();
//        String query = "SELECT id, pickup_location, dropoff_location, fare, ride_status, timestamp FROM rides WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driverId);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Ride ride = new Ride();
//                ride.setId(rs.getInt("id"));
//                ride.setPickupLocation(rs.getString("pickup_location"));
//                ride.setDropoffLocation(rs.getString("dropoff_location"));
//                ride.setFare(rs.getDouble("fare"));
//                ride.setStatus(rs.getString("ride_status"));
//                ride.setTimestamp(rs.getTimestamp("timestamp"));
//
//                rides.add(ride);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rides;
//    }
//
//    // Method to get driver statistics (total rides, earnings, rating)
//   public Driver getDriverStats(int driverId) {
//    String query = "SELECT total_rides, earnings, rating FROM drivers WHERE id = ?";
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//        stmt.setInt(1, driverId);
//        ResultSet rs = stmt.executeQuery();
//        if (rs.next()) {
//            Driver driver = new Driver();
//            driver.setDriverId(driverId);
//            driver.setTotalRides(rs.getInt("total_rides"));
//            driver.setEarnings(rs.getDouble("earnings"));
//            driver.setRating(rs.getDouble("rating"));
//            return driver;
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return null;
//}
//
//
//    // Method to assign a ride to a driver
//    public boolean assignRideToDriver(int rideId, int driverId) {
//        String query = "UPDATE rides SET driver_id = ?, ride_status = 'Assigned' WHERE id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setInt(1, driverId);
//            stmt.setInt(2, rideId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//}











//package DAO;
//
///**
// *
// * @author zainr
// */
//
//
//import Utils.DBConfig;
//import Model.User;
//import Model.Driver;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DriverDAO {
//
//    // ✅ Assign a ride to a driver
//    public boolean assignRideToDriver(int driverId, int rideId) {
//        String sql = "UPDATE rides SET driver_id = ?, status = 'Accepted' WHERE id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, driverId);
//            stmt.setInt(2, rideId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error assigning ride to driver: " + e.getMessage());
//            return false;
//        }
//    }
//
//// ✅ Update driver personal and vehicle details
//public boolean updateDriverInfo(int driverId, String name, String phone, String vehicleType, String vehicleNumber) {
//    String sql = "UPDATE users u JOIN drivers d ON u.id = d.user_id " +
//                 "SET u.name = ?, u.phone = ?, d.vehicle_type = ?, d.vehicle_number = ? " +
//                 "WHERE u.id = ?";
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//        stmt.setString(1, name);
//        stmt.setString(2, phone);
//        stmt.setString(3, vehicleType);
//        stmt.setString(4, vehicleNumber);
//        stmt.setInt(5, driverId);
//        return stmt.executeUpdate() > 0;
//    } catch (SQLException e) {
//        System.err.println("Error updating driver profile: " + e.getMessage());
//        return false;
//    }
//}
//
//   
//
//    // ✅ Update driver vehicle details
//    public boolean updateDriverVehicle(int driverId, String vehicleType, String vehicleNumber, int seatingCapacity) {
//        String sql = "UPDATE drivers SET vehicle_type = ?, vehicle_number = ?, seating_capacity = ? WHERE user_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, vehicleType);
//            stmt.setString(2, vehicleNumber);
//            stmt.setInt(3, seatingCapacity);
//            stmt.setInt(4, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error updating driver vehicle details: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // ✅ Get all active drivers
//    public List<User> getActiveDrivers() {
//        List<User> drivers = new ArrayList<>();
//        String sql = "SELECT id, username, email, role FROM users WHERE LOWER(role) = 'driver' AND is_active = TRUE";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                User driver = new User();
//                driver.setId(rs.getInt("id"));
//                driver.setUsername(rs.getString("username"));
//                driver.setEmail(rs.getString("email"));
//                driver.setRole(rs.getString("role"));
//                drivers.add(driver);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching active drivers: " + e.getMessage());
//        }
//        return drivers;
//    }
//
//      // ✅ Get driver details by user ID
//    public Driver getDriverByUserId(int userId) {
//        String sql = "SELECT * FROM drivers WHERE user_id = ?";
//        Driver driver = null;
//
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    driver = new Driver();
//                    driver.setDriverId(rs.getInt("driver_id"));
//                    driver.setUserId(rs.getInt("user_id"));
//                    driver.setLicenseNumber(rs.getString("license_number"));
//                    driver.setVehicleType(rs.getString("vehicle_type"));
//                    driver.setVehicleNumber(rs.getString("vehicle_number"));
//                    driver.setSeatingCapacity(rs.getInt("seating_capacity"));
//                    driver.setApproved(rs.getBoolean("is_approved"));
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching driver details: " + e.getMessage());
//        }
//        return driver;
//    }
//
//
//    // ✅ Update driver availability status (Available, Busy, Offline)
//    public boolean updateDriverStatus(int driverId, String status) {
//        String sql = "UPDATE drivers SET status = ? WHERE user_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, status);
//            stmt.setInt(2, driverId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error updating driver status: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // ✅ Get driver earnings
//    public double getDriverEarnings(int driverId) {
//        String sql = "SELECT SUM(fare) AS total_earnings FROM rides WHERE driver_id = ? AND status = 'Completed'";
//        double earnings = 0.0;
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, driverId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    earnings = rs.getDouble("total_earnings");
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching driver earnings: " + e.getMessage());
//        }
//        return earnings;
//    }
//
//    // ✅ Get driver ride history
//    public List<String> getDriverRides(int driverId) {
//        List<String> rides = new ArrayList<>();
//        String sql = "SELECT ride_id, pickup_location, dropoff_location, fare, status FROM rides WHERE driver_id = ?";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, driverId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    String rideDetails = "Ride ID: " + rs.getInt("ride_id") +
//                            ", Pickup: " + rs.getString("pickup_location") +
//                            ", Dropoff: " + rs.getString("dropoff_location") +
//                            ", Fare: " + rs.getDouble("fare") +
//                            ", Status: " + rs.getString("status");
//                    rides.add(rideDetails);
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching driver rides: " + e.getMessage());
//        }
//        return rides;
//    }
//}
