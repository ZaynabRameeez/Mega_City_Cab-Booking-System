/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.VehicleType;
import Utils.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class VehicleTypeDAO {

    // Fetch all vehicle types
//    public List<VehicleType> getAllVehicleTypes() {
//        List<VehicleType> vehicles = new ArrayList<>();
//        String query = "SELECT * FROM vehicle_types";
//        
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//             
//            while (rs.next()) {
//                vehicles.add(new VehicleType(
//                    rs.getInt("id"),
//                    rs.getString("type_name"),
//                    rs.getDouble("base_fare"),
//                    rs.getDouble("per_km_rate"),
//                    rs.getString("default_image_url"),
//                    rs.getTimestamp("created_at")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//return vehicles;
//    }
    public List<VehicleType> getAllVehicleTypes() {
    List<VehicleType> vehicleTypes = new ArrayList<>();
    String query = "SELECT * FROM vehicle_types"; // Example query
    try (Connection connection = DBConfig.getConnection();
         PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            VehicleType vehicle = new VehicleType();
            vehicle.setId(rs.getInt("id"));
            vehicle.setType_name(rs.getString("type_name"));
            vehicle.setBase_fare(rs.getDouble("base_fare"));
            vehicle.setPer_km_rate(rs.getDouble("per_km_rate"));
            vehicle.setDefault_image_url(rs.getString("default_image_url"));
            vehicle.setCreatedAt(rs.getTimestamp("created_at"));
            vehicleTypes.add(vehicle);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return vehicleTypes;
}
    


    // Add a new vehicle type
    public boolean addVehicleType(String typeName, double baseFare, double perKmRate, String imageUrl) {
        String query = "INSERT INTO vehicle_types (type_name, base_fare, per_km_rate, default_image_url) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, typeName);
            stmt.setDouble(2, baseFare);
            stmt.setDouble(3, perKmRate);
            stmt.setString(4, imageUrl);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Edit vehicle type
    public boolean updateVehicleType(int id, String typeName, double baseFare, double perKmRate, String imageUrl) {
        String query = "UPDATE vehicle_types SET type_name=?, base_fare=?, per_km_rate=?, default_image_url=? WHERE id=?";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, typeName);
            stmt.setDouble(2, baseFare);
            stmt.setDouble(3, perKmRate);
            stmt.setString(4, imageUrl);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete vehicle type
    public boolean deleteVehicleType(int id) {
        String query = "DELETE FROM vehicle_types WHERE id=?";
        
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Fetch a vehicle type by ID
public VehicleType getVehicleTypeById(int id) {
    VehicleType vehicle = null;
     String query = "SELECT * FROM vehicle_types ORDER BY created_at ASC";
    
    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, id); // Set the vehicle type ID to the query
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                vehicle = new VehicleType(
                    rs.getInt("id"),
                    rs.getString("type_name"),
                    rs.getDouble("base_fare"),
                    rs.getDouble("per_km_rate"),
                    rs.getString("default_image_url")!= null ? rs.getString("default_image_url") : "default.png",
                    rs.getTimestamp("created_at")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return vehicle;
}

}






//
//
//public class VehicleTypeDAO {
//    public boolean addVehicleType(VehicleType vehicleType) {
//        String sql = "INSERT INTO vehicle_types (type_name, base_fare, per_km_rate, default_image_url, created_at) VALUES (?, ?, ?, ?, NOW())";
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, vehicleType.getType_name());
//            stmt.setDouble(2, vehicleType.getBase_fare());
//            stmt.setDouble(3, vehicleType.getPer_km_rate());
//            stmt.setString(4, vehicleType.getDefault_image_url());
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public List<VehicleType> getAllVehicleTypes() {
//        List<VehicleType> vehicleTypes = new ArrayList<>();
//        String sql = "SELECT * FROM vehicle_types ORDER BY created_at ASC";
//        try (Connection conn = DBConfig.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
////                VehicleType vehicle = new VehicleType(
//                    vehicles.add(new VehicleType(
//                        rs.getInt("id"),
//                        rs.getString("type_name"),
//                        rs.getDouble("base_fare"),
//                        rs.getDouble("per_km_rate"),
//                        rs.getString("default_image_url"),
//                        rs.getTimestamp("created_at")
//                ));
//                vehicleTypes.add(vehicle);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return vehicleTypes;
//    }
//    public void deleteVehicleType(int id) {
//    try (Connection conn = DBConfig.getConnection()) {
//        String sql = "DELETE FROM vehicle_types WHERE id=?";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setInt(1, id);
//        stmt.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    }
//public void updateVehicleType(VehicleType vehicleType) {
//    try (Connection conn = DBConfig.getConnection()) {
//        String sql = "UPDATE vehicle_types SET type_name=?, base_fare=?, per_km_rate=?, default_image_url=? WHERE id=?";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setString(1, vehicleType.getType_name());
//        stmt.setDouble(2, vehicleType.getBase_fare());
//        stmt.setDouble(3, vehicleType.getPer_km_rate());
//        stmt.setString(4, vehicleType.getDefault_image_url());
//        stmt.setInt(5, vehicleType.getId());
//        stmt.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//     }
//        return false;
//    }
//
//public VehicleType getVehicleTypeById(int id) {
//    VehicleType vehicleType = null;
//    String query = "SELECT * FROM vehicle_types WHERE id = ?";
//    
//    try (Connection conn = DBConfig.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//        
//        stmt.setInt(1, id);
//        ResultSet rs = stmt.executeQuery();
//        
//        if (rs.next()) {
//            vehicleType = new VehicleType(
//                rs.getInt("id"),
//                rs.getString("type_name"),
//                rs.getDouble("base_fare"),
//                rs.getDouble("per_km_rate"),
//                rs.getString("default_image_url"),
//                rs.getTimestamp("created_at")
//            );
//        } else {
//            System.out.println("Vehicle Type with ID " + id + " not found in the database.");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return vehicleType;
//}
//

//}
